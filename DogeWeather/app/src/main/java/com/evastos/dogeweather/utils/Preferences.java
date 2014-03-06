package com.evastos.dogeweather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.evastos.dogeweather.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Class Preferences.
 *
 * Manages shared preferences.
 *
 * @author Eva
 */
public class Preferences {

    private static final String PREFERENCES_LOCATIONS = "preferences_locations";
    private static final String PREFERENCES_SELECTED_LOCATION = "preferences_selected_location";
    private static final String PREFERENCES_UNITS = "preferences_units";

    private static SharedPreferences sSharedPreferences = null;

    private static Preferences sPreferences = null;

    private Context mContext;

    private Preferences(final Context context) {
        setContext(context);
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Gets instance of Preferences.
     *
     * @param context the context
     * @return instance of Preferences
     */
    public static Preferences getInstance(final Context context) {
        if (sPreferences == null) {
            sPreferences = new Preferences(context);
        }
        return sPreferences;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    /**
     * Gets user locations from preferences.
     *
     * @return user location list if it exists, else default locations
     */
    public List<String> getUserLocations() {
        List<String> userLocations;
        String serializedLocations = sSharedPreferences.getString(PREFERENCES_LOCATIONS, null);
        if (serializedLocations == null) {
            userLocations =
                    Arrays.asList(getContext().getResources().getStringArray(R.array.default_locations));
        } else {
            userLocations = Arrays.asList(TextUtils.split(serializedLocations, ";"));
        }
        return new ArrayList<String>(userLocations);
    }

    /**
     * Sets user locations to preferences.
     *
     * @param locations new locations
     */
    public void setUserLocations(List<String> locations) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putString(PREFERENCES_LOCATIONS, TextUtils.join(";", locations));
        editor.commit();
    }

    /**
     * Sets user selected location.
     *
     * @param selectedLocationIndex location index in a list
     */
    public void setSelectedLocation(int selectedLocationIndex) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putInt(PREFERENCES_SELECTED_LOCATION, selectedLocationIndex);
        editor.commit();
    }

    /**
     * Gets selected locations index.
     *
     * @return selected location index, default: o
     */
    public int getSelectedLocation() {
        return sSharedPreferences.getInt(PREFERENCES_SELECTED_LOCATION, 0);
    }

    /**
     * Sets units to preferences.
     *
     * @see com.evastos.dogeweather.utils.Units
     *
     * @param units new units
     */
    public void setUnits(Units units) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putInt(PREFERENCES_UNITS, units.ordinal());
        editor.commit();
    }

    /**
     * Gets selected units from preferences.
     *
     * @return units, default: metric
     */
    public Units getUnits() {
        Units savedUnits = Units.values()[sSharedPreferences.getInt(PREFERENCES_UNITS, 0)];
        return savedUnits;
    }

}
