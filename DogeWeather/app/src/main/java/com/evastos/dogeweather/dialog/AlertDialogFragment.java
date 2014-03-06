package com.evastos.dogeweather.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.evastos.dogeweather.R;
import com.evastos.dogeweather.utils.Const;

/**
 * The Class AlertDialogFragment.
 *
 * @author Eva
 */
public class AlertDialogFragment extends DialogFragment {

    /**
     * Gets AlertDialogFragment instance.
     *
     * @see com.evastos.dogeweather.dialog.DialogType
     *
     * @param message the message
     * @param type the dialog type
     * @return AlertiDialogFragment instance
     */
	public static AlertDialogFragment getInstance(String message,
			DialogType type) {
		AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
		Bundle args = new Bundle();
		args.putSerializable(Const.MESSAGE_EXTRA, message);
		args.putSerializable(Const.DIALOG_TYPE_EXTRA, type);
		alertDialogFragment.setArguments(args);
		return alertDialogFragment;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String message = (String) getArguments().getSerializable(Const.MESSAGE_EXTRA);
		DialogType type = (DialogType) getArguments().getSerializable(
				Const.DIALOG_TYPE_EXTRA);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String title = null;
		switch (type) {
		case INFO:
				title = getString(R.string.action_info);
			builder.setNeutralButton(getString(R.string.close), mOnButtonClickListener);
			builder.setIcon(R.drawable.ic_action_about_dark);
			break;
		case ERROR:
				title = getString(R.string.action_error);
			builder.setNeutralButton(getString(R.string.ok),
                    mOnButtonClickListener);
			builder.setIcon(R.drawable.ic_action_error_dark);
			break;
		default:
			break;
		}
		if (title != null) {
			builder.setTitle(title);
		}
		if (message != null) {
			builder.setMessage(message);
		}
		return builder.create();
	}

	private DialogInterface.OnClickListener mOnButtonClickListener = new OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};
}
