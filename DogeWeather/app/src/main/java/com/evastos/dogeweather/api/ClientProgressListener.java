package com.evastos.dogeweather.api;

/**
 * The Interface ClientProgressListener.
 *
 * Listens to client progress events.
 *
 * @author Eva
 */
public interface ClientProgressListener {

	/**
	 * On start.
	 * 
	 * @param client the client
	 */
	public void onStart(BaseClient client);

	/**
	 * On finish.
	 * 
	 * @param client the client
	 */
	public void onFinish(BaseClient client);

}