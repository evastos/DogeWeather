package com.evastos.dogeweather.local_weather;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.evastos.dogeweather.R;
import com.evastos.dogeweather.application.BaseActivity;
import com.evastos.dogeweather.locations.LocationsActivity;
import com.evastos.dogeweather.utils.Const;
import com.evastos.dogeweather.utils.NetworkUtils;
import com.evastos.dogeweather.utils.Preferences;
import com.evastos.dogeweather.utils.Units;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class LocalWeatherActivity.
 *
 * @author Eva
 */
public class LocalWeatherActivity extends BaseActivity {

    public interface OnRefreshListener {
        public void onRefresh();
    }

    public interface OnUnitsChangeListener {
        public void onUnitsChanged(Units units);
    }

    private static final int REQUEST_UPDATE_LOCATIONS = 1001;

    private ViewPager mLocalWeatherPager;

    private LocalWeatherPagerAdapter mLocalWeatherPagerAdapter;

    private List<String> mLocations;

    private int mSelectedLocationIndex = 0;

    private Units mUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_local_weather);

        mLocalWeatherPager = (ViewPager) findViewById(R.id.view_pager_local_weather);

        mLocations = Preferences.getInstance(this).getUserLocations();
        mSelectedLocationIndex = Preferences.getInstance(this).getSelectedLocation();
        mUnits = Preferences.getInstance(this).getUnits();

        mLocalWeatherPagerAdapter = new LocalWeatherPagerAdapter(getFragmentManager());
        mLocalWeatherPager.setAdapter(mLocalWeatherPagerAdapter);
        mLocalWeatherPager.setOnPageChangeListener(mPageChangeListener);

        setUnitsDropDownMenu(getActionBar());

        handleNetworkConnection(NetworkUtils.isConnected(this));

    }

    @Override
    protected void onStop() {
        super.onStop();
        Preferences.getInstance(this).setSelectedLocation(mSelectedLocationIndex);
    }

    private ViewPager.OnPageChangeListener mPageChangeListener =
            new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i2) {
        }

        @Override
        public void onPageSelected(int i) {
            mSelectedLocationIndex = i;
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    };

    private void setUnitsDropDownMenu(final ActionBar actionBar) {
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        final ArrayAdapter<Units> unitsAdapter = new ArrayAdapter<Units>(this, R.layout.spinner_item_units, Units.values());
        unitsAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_units);
        actionBar.setListNavigationCallbacks(unitsAdapter, mUnitsListener);
        actionBar.setSelectedNavigationItem(mUnits.ordinal());
    }

    private ActionBar.OnNavigationListener mUnitsListener = new ActionBar.OnNavigationListener() {

        @Override
        public boolean onNavigationItemSelected(int itemPosition, long itemId) {
            Units unitsSelected = Units.values()[itemPosition];
            if (unitsSelected != mUnits) {
                mUnits = unitsSelected;
                Preferences.getInstance(LocalWeatherActivity.this).setUnits(unitsSelected);
                invokeUnitsChangeListeners(unitsSelected);
            }
            return false;
        }
    };

    @Override
    protected void handleNetworkConnection(boolean isConnected) {
        super.handleNetworkConnection(isConnected);

        if (isConnected) {
            refreshLocalWeather();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_local_weather, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_refresh:
                refreshLocalWeather();
                return true;
            case R.id.action_locations:
                openLocations();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_UPDATE_LOCATIONS) {
            if (resultCode == RESULT_OK) {
                mLocations = Preferences.getInstance(this).getUserLocations();
                refreshLocalWeather();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void refreshLocalWeather() {
        if (mLocalWeatherPagerAdapter == null) {
            mLocalWeatherPagerAdapter = new LocalWeatherPagerAdapter(getFragmentManager());
            mLocalWeatherPager.setAdapter(mLocalWeatherPagerAdapter);
        } else {
            mLocalWeatherPagerAdapter.notifyDataSetChanged();
        }
        mLocalWeatherPager.setCurrentItem(mSelectedLocationIndex);
    }

    private void invokeUnitsChangeListeners(Units units) {
        for (Fragment fragment : mLocalWeatherPagerAdapter.getActiveFragments()) {
            if (fragment instanceof OnUnitsChangeListener) {
                ((OnUnitsChangeListener) fragment).onUnitsChanged(units);
            }
        }

    }

    private void openLocations() {
        startActivityForResult(new Intent(this, LocationsActivity.class), REQUEST_UPDATE_LOCATIONS);
    }

    /**
     * A {@link android.support.v4.app.FragmentStatePagerAdapter} that returns a fragment
     * representing an object in the collection.
     */
    public class LocalWeatherPagerAdapter extends FragmentStatePagerAdapter {

        List<WeakReference<Fragment>> fragmentReferenceList = new ArrayList<WeakReference<Fragment>>();

        public LocalWeatherPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public List<Fragment> getActiveFragments() {
            ArrayList<Fragment> activeFragments = new ArrayList<Fragment>();
            for (WeakReference<Fragment> reference : fragmentReferenceList) {
                Fragment fragment = reference.get();
                if (fragment != null && fragment.isVisible()) {
                    activeFragments.add(fragment);
                }
            }
            return activeFragments;
        }

        @Override
        public int getItemPosition(Object object) {
            if (object instanceof OnRefreshListener) {
                ((OnRefreshListener) object).onRefresh();
            }
            return super.getItemPosition(object);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new LocalWeatherFragment();
            Bundle args = new Bundle();
            args.putString(Const.LOCATION_EXTRA, mLocations.get(i));
            fragment.setArguments(args);
            fragmentReferenceList.add(new WeakReference<Fragment>(fragment));
            return fragment;
        }

        @Override
        public int getCount() {
            if (mLocations == null) {
                return 0;
            } else {
                return mLocations.size();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mLocations.size() > 0) {
                String areaName = mLocations.get(position).toUpperCase().split(", ")[0];
                return areaName;
            } else {
                return "";
            }
        }
    }

}
