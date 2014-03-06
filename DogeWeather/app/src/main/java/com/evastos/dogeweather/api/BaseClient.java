package com.evastos.dogeweather.api;

import android.content.Context;
import android.os.AsyncTask;

import com.evastos.dogeweather.R;
import com.evastos.dogeweather.api.model.BaseRequest;
import com.evastos.dogeweather.api.model.ErrorResponse;
import com.evastos.dogeweather.api.parser.ErrorResponseParser;
import com.evastos.dogeweather.api.parser.ResponseParser;

/**
 * The Class BaseClient.
 *
 * @author Eva
 */
public abstract class BaseClient {

    protected static final String BASE_API_URL = "http://api.worldweatheronline.com/free/v1";

    private ClientResultListener mResultListener;

    private ClientProgressListener mProgressListener;

    private Context mContext;

    private ResponseParser mResponseParser;

    private ErrorResponseParser mErrorResponseParser;

    private HttpMethod mHttpMethod;

    private BaseRequest mBaseRequest;

    private String mApiUrl;

    /**
     * Instantiates a new base client.
     *
     * @param context        the context
     * @param httpMethod     the http method
     * @param responseParser the response parser
     */
    protected BaseClient(Context context, String apiUrl, HttpMethod httpMethod,
                         ResponseParser responseParser) {
        mContext = context;
        mApiUrl = apiUrl;
        mHttpMethod = httpMethod;
        mResponseParser = responseParser;
        mErrorResponseParser = new ErrorResponseParser();

    }

    public void setProgressListener(ClientProgressListener progressListener) {
        this.mProgressListener = progressListener;
    }

    public void setResultListener(ClientResultListener resultListener) {
        this.mResultListener = resultListener;
    }

    public String getApiUrl() {
        return mApiUrl;
    }

    protected ErrorResponseParser getErrorResponseParser() {
        return mErrorResponseParser;
    }

    private ResponseParser getResponseParser() {
        return mResponseParser;
    }

    private HttpMethod getHttpMethod() {
        return mHttpMethod;
    }

    /**
     * Executes HTTP request.
     *
     * @see com.evastos.dogeweather.api.model.BaseRequest
     *
     * @param baseRequest
     */
    protected final void runClient(BaseRequest baseRequest) {
        String urlWithParams = baseRequest.addParamsToUrl(getApiUrl());

        new HttpClientAsync(urlWithParams).execute();

    }

    private Context getContext() {
        return mContext;
    }

    protected boolean isClientSuccess(String response) {
        if (response != null) {
            ErrorResponse errorResponse = getErrorResponseParser().parseResponse(response);
            return errorResponse.getData() == null || errorResponse.getData().getErrorList() == null;
        } else {
            return false;
        }
    }

    /**
     * The Enum for HTTP method.
     */
    protected enum HttpMethod {

        POST, GET, DELETE, PUT
    }

    /**
     * The Class HttpClientAsync.
     *
     * Executes HTTP request, handles response and invokes client listeners.
     */
    protected class HttpClientAsync extends AsyncTask<Void, Void, Boolean> {

        private String mUrl;

        private String mResponse;

        private ErrorResponse mErrorResponse;

        /**
         * Instantiates a new AsyncTask for executing HTTP requests.
         *
         * @param url the api url
         */
        public HttpClientAsync(String url) {
            mUrl = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (mProgressListener != null) {
                mProgressListener.onStart(BaseClient.this);
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            mResponse = ConnectionHandler.openHttpConnection(mUrl, getHttpMethod());

            if (mResponse != null) {
                mErrorResponse = getErrorResponseParser().parseResponse(mResponse);
            }
            return isClientSuccess(mResponse);

        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if (mResultListener != null) {
                if (isSuccess) {
                    mResultListener.onSuccess(BaseClient.this,
                            getResponseParser().parseResponse(mResponse));
                } else {
                    mResultListener.onError(BaseClient.this,
                            getErrorMessage());
                }
            }
            if (mProgressListener != null) {
                mProgressListener.onFinish(BaseClient.this);
            }
        }

        private String getErrorMessage() {
            if (mErrorResponse != null) {
                return mErrorResponse.getMessage();
            } else {
                return getContext()
                        .getString(R.string.default_client_error_message);
            }
        }

    }

}
