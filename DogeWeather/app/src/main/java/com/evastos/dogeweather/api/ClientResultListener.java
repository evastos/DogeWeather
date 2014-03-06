package com.evastos.dogeweather.api;

/**
 * The Interface ClientResultListener.
 *
 * Listens to client result events.
 *
 * @author Eva
 */
public interface ClientResultListener<T> {

	/**
	 * On success.
	 * 
	 * @param client the client
	 * @param result the result object
	 */
	public void onSuccess(BaseClient client, T result);


    /**
     * On error.
     *
     * @param client the client
     * @param errorMessage the error message
     */
	public void onError(BaseClient client, String errorMessage);

}
