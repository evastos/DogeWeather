package com.evastos.dogeweather.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * The Class NetworkUtils.
 *
 * @author Eva
 */
public class NetworkUtils {

	/**
	 * Checks for network connection.
	 * 
	 * @param context
	 *            the context
	 * @return true, if connected to a network
	 */
	public static boolean isConnected(final Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
		return activeNetwork != null && activeNetwork.isConnected();
	}

}
