package com.evastos.dogeweather.locations;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.evastos.dogeweather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class LocationsAdapter.
 *
 * @author Eva
 */
public class LocationsAdapter extends ArrayAdapter<String> {

    public interface OnSelectionChangeListener {
        public void onSelectionChanged(int selectedItems);
    }

    private List<String> mLocations;

    private Activity mActivity;

    private SparseBooleanArray mSelectedItemIds;

    private OnSelectionChangeListener mOnSelectionChangeListener;

    private boolean mIsInEditMode;

    public boolean isInEditMode() {
        return mIsInEditMode;
    }

    public void setIsInEditMode(boolean sInEditMode) {
        this.mIsInEditMode = sInEditMode;
        notifyDataSetChanged();
    }

    public void setOnSelectionChangeListener(OnSelectionChangeListener listener) {
        mOnSelectionChangeListener = listener;
    }

    public void removeOnSelectionChangeListener() {
        mOnSelectionChangeListener = null;
    }

    public LocationsAdapter(Activity activity, List<String> locations) {
        super(activity, R.id.text_view_location, locations);
        mActivity = activity;
        mLocations = locations;
        mSelectedItemIds = new SparseBooleanArray();
    }

    public List<String> getLocations() {
        return mLocations;
    }

    public void setLocations(List<String> locations) {
        mLocations.clear();
        mLocations.addAll(locations);
        deselectAll();
    }

    private void invokeSelectionChangeListener() {
        if (mOnSelectionChangeListener != null) {
            mOnSelectionChangeListener.onSelectionChanged(getSelectedCount());
        }
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemIds.get(position));
    }

    public void deselectAll() {
        mSelectedItemIds.clear();
        invokeSelectionChangeListener();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value) {
            mSelectedItemIds.put(position, value);
        } else {
            mSelectedItemIds.delete(position);
        }
        invokeSelectionChangeListener();
        notifyDataSetChanged();
    }

    public void selectAll() {
        for (int position = 0; position < getCount(); position++) {
            selectView(position, true);
        }
        invokeSelectionChangeListener();
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemIds.size();// mSelectedCount;
    }

    public void removeSelectedItems() {
        List<String> selectedLocations = new ArrayList<String>();
        for (int position = 0; position < getCount(); position++) {
            if (mSelectedItemIds.get(position) == true) {
                selectedLocations.add(getItem(position));
            }
        }
        mLocations.removeAll(selectedLocations);
        deselectAll();
    }


    @Override
    public String getItem(int position) {
        if (position < mLocations.size()) {
            return super.getItem(position);
        } else {
            return null;
        }
    }


    @Override
    public int getCount() {
        if (mLocations == null) {
            return 0;
        }
        return mLocations.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_view_item_location, parent, false);
            holder = new ViewHolder();
            holder.textViewLocation = (TextView) v.findViewById(R.id.text_view_location);
            holder.checkBoxLocation = (CheckBox) v.findViewById(R.id.check_box_location);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        final String location = getItem(position);

        if (location != null) {
            holder.setLocationText(location);
        }

        int backgroundResource;
        if (isInEditMode()) {
            boolean isLocationSelected = mSelectedItemIds.get(position) == true;
            if (isLocationSelected) {
                backgroundResource = R.color.light_blue;
            } else {
                backgroundResource = android.R.color.transparent;
            }
            holder.showCheckBox(isLocationSelected);
        } else {
            backgroundResource = R.drawable.list_view_item_selector;
            holder.hideCheckBox();
        }
        v.setBackgroundResource(backgroundResource);

        return v;
    }

    class ViewHolder {
        TextView textViewLocation;
        CheckBox checkBoxLocation;

        public void setLocationText(CharSequence text) {
            textViewLocation.setText(text);
        }

        public void showCheckBox(boolean checked) {
            this.checkBoxLocation.setVisibility(View.VISIBLE);
            this.checkBoxLocation.setChecked(checked);
        }

        public void hideCheckBox() {
            this.checkBoxLocation.setVisibility(View.GONE);
        }
    }

}
