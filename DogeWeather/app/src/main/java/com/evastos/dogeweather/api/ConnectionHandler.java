package com.evastos.dogeweather.api;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * The Class ConnectionHandler.
 *
 * @author Eva
 */
public class ConnectionHandler {

    private static final int CONNECTION_TIMEOUT = 30000;

    private static final int SOCKET_TIMEOUT = 30000;

    /**
     * Read stream.
     *
     * @param inputStream the inputStream
     * @return the string response
     */
    private static String readStream(InputStream inputStream) {
        String response = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream, Charset.forName("UTF-8")), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStream.close();
            response = stringBuilder.toString();

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        return response;
    }

    /**
     * Opens HTTP connection.
     *
     * @see com.evastos.dogeweather.api.BaseClient.HttpMethod
     *
     * @param urlString the URL
     * @param httpMethod the HTTP method
     * @return response
     */
    public static String openHttpConnection(String urlString, BaseClient.HttpMethod httpMethod) {

        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();

            if (!(urlConnection instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }

            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            httpConnection.setReadTimeout(SOCKET_TIMEOUT);
            httpConnection.setRequestMethod(httpMethod.toString());
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return readStream(httpConnection.getInputStream());
            }
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
