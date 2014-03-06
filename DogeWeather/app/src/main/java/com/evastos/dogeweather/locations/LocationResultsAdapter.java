package com.evastos.dogeweather.locations;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.evastos.dogeweather.R;

import java.util.List;

/**
 * The Class LocationResultsAdapter.
 *
 * @author Eva
 */
public class LocationResultsAdapter extends ArrayAdapter<String> {

    public LocationResultsAdapter(Context context, List<String> objects) {
        super(context, R.id.text_view_location_result, objects);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getSpinnerView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getSpinnerView(position, convertView, parent);
    }

    private View getSpinnerView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        final LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (inflater != null) {
            view = inflater.inflate(R.layout.spinner_item_location, parent, false);
            TextView textViewLocationResult = (TextView)
                    view.findViewById(R.id.text_view_location_result);

            textViewLocationResult.setText(getItem(position));

            int textColor;
            final Resources resources = getContext().getResources();
            if (position == 0) {
                textColor = resources.getColor(R.color.gray);
            } else {
                textColor = resources.getColor(R.color.dark_blue);
            }
            textViewLocationResult.setTextColor(textColor);

        }

        return view;
    }
}
