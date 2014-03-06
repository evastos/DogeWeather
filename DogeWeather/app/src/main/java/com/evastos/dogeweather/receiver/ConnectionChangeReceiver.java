package com.evastos.dogeweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.evastos.dogeweather.utils.Const;
import com.evastos.dogeweather.utils.NetworkUtils;

/**
 * The Class ConnectionChangeReceiver.
 *
 * @author Eva
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {

	public static final String CONNECTION_CHANGE_INTENT = "com.evastos.mttweather.CONNECTION_CHANGE";

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction() != null) {
			Intent connectionChangeIntent = new Intent();
			connectionChangeIntent.putExtra(Const.IS_CONNECTED_EXTRA,
                    NetworkUtils.isConnected(context));
			connectionChangeIntent.setAction(CONNECTION_CHANGE_INTENT);
			context.sendBroadcast(connectionChangeIntent);
		}
	}
}
