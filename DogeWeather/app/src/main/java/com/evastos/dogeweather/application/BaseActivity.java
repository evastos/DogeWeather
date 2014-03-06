package com.evastos.dogeweather.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.evastos.dogeweather.R;
import com.evastos.dogeweather.animation.AnimationFactory;
import com.evastos.dogeweather.dialog.AlertDialogFragment;
import com.evastos.dogeweather.dialog.DialogType;
import com.evastos.dogeweather.receiver.ConnectionChangeReceiver;
import com.evastos.dogeweather.utils.Const;
import com.evastos.dogeweather.utils.NetworkUtils;

/**
 * The Class BaseActivity.
 *
 * @author Eva
 */
public class BaseActivity extends FragmentActivity {

	private AlertDialogFragment mAlertDialogFragment;

	private View mNoNetworkConnectionView;

	private boolean mIsConnected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mIsConnected = NetworkUtils.isConnected(this);

		registerReceiver(mNetworkConnectionReceiver, new IntentFilter(
				ConnectionChangeReceiver.CONNECTION_CHANGE_INTENT));

	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mNetworkConnectionReceiver);
		super.onDestroy();
	}

    /**
     * Displays toast.
     *
     * @see android.widget.Toast
     *
     * @param message the message
     */
    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Displays error dialog.
     *
     * @param message the message
     */
	public void showErrorDialog(String message) {
		mAlertDialogFragment = AlertDialogFragment.getInstance(message,
                DialogType.ERROR);
		getSupportFragmentManager().beginTransaction()
				.add(mAlertDialogFragment, "error_alert").commit();
	}


	/**
	 * Handles network connection.
	 * 
	 * Displays no network connection view if device is not connected to a
	 * network.
	 * 
	 * @param isConnected
	 *            the is connected
	 */
	protected void handleNetworkConnection(final boolean isConnected) {
		createNoNetworkConnectionAlert();
		if (isConnected) {
			hideNoNetworkConnectionAlert();
		} else {
			showNoNetworkConnectionAlert();
		}

	}

	/**
	 * Creates the no network connection alert if it has not yet been created.
	 */
	private void createNoNetworkConnectionAlert() {
		if (mNoNetworkConnectionView == null) {
			ViewGroup rootLayout = (ViewGroup) getWindow().getDecorView()
					.findViewById(android.R.id.content);
			mNoNetworkConnectionView = getLayoutInflater().inflate(
					R.layout.layout_no_network_connection, null);
			rootLayout.addView(
					mNoNetworkConnectionView,
					new LayoutParams(LayoutParams.MATCH_PARENT,
							(int) getResources().getDimension(
									R.dimen.no_network_connection_height)));
		}
	}

	/**
	 * Show no network connection alert.
	 */
	private void showNoNetworkConnectionAlert() {
		mNoNetworkConnectionView.setVisibility(View.VISIBLE);
		mNoNetworkConnectionView.startAnimation(AnimationFactory
				.inFromTopAnimation(
						getResources().getInteger(
								R.integer.translate_anim_duration),
						new LinearInterpolator()));
	}

	/**
	 * Hides no network connection alert.
	 */
	private void hideNoNetworkConnectionAlert() {
		mNoNetworkConnectionView.startAnimation(AnimationFactory
				.outToTopAnimation(
						getResources().getInteger(
								R.integer.translate_anim_duration),
						new LinearInterpolator()));
		mNoNetworkConnectionView.setVisibility(View.GONE);
	}

	/** The connection change receiver. */
	private BroadcastReceiver mNetworkConnectionReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			boolean isIntentOk = intent.getAction() != null
					&& intent.getExtras() != null;

			boolean shouldRespondToIntent = isIntentOk
					&& intent.getAction().equals(
							ConnectionChangeReceiver.CONNECTION_CHANGE_INTENT);

			if (shouldRespondToIntent) {
				boolean isConnected = intent.getExtras().getBoolean(
						Const.IS_CONNECTED_EXTRA);

				if (mIsConnected != isConnected) {
					mIsConnected = isConnected;
					handleNetworkConnection(isConnected);
				}

			}

		}
	};

    /* Shows software keyboard */
    protected void showInputMethod(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        view.requestFocus();
    }

}
