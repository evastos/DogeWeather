package com.evastos.dogeweather.locations;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;

import com.evastos.dogeweather.R;
import com.evastos.dogeweather.api.BaseClient;
import com.evastos.dogeweather.api.ClientProgressListener;
import com.evastos.dogeweather.api.ClientResultListener;
import com.evastos.dogeweather.api.LocationSearchClient;
import com.evastos.dogeweather.api.model.Location;
import com.evastos.dogeweather.api.model.LocationQuery;
import com.evastos.dogeweather.api.model.LocationSearch;
import com.evastos.dogeweather.api.model.LocationSearchRequest;
import com.evastos.dogeweather.application.BaseActivity;
import com.evastos.dogeweather.utils.NetworkUtils;
import com.evastos.dogeweather.utils.Preferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eva on 26.02.14..
 */
public class LocationsActivity extends BaseActivity implements LocationsAdapter.OnSelectionChangeListener {

    private List<String> mLocations;

    private List<String> mLocationsBackup;

    private ListView mListViewLocations;

    private LocationsAdapter mLocationsAdapter;

    private ActionMode mActionMode;

    boolean mWasActionCanceled = false;

    private LinearLayout mLayoutSearchLocation;

    private SearchView mSearchViewLocation;

    private LocationSearchClient mLocationSearchClient;

    private ProgressBar mProgressBarSearch;

    private Spinner mSpinnerSearchResults;

    private LocationResultsAdapter mLocationResultsAdapter;

    private AbsListView.MultiChoiceModeListener mMultiChoiceModeListener =
            new AbsListView.MultiChoiceModeListener() {

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position,
                                              long id, boolean checked) {
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            // Respond to clicks on the actions in the CAB
            switch (item.getItemId()) {
                case R.id.action_add:
                    toggleLocationSearch();
                    return true;
                case R.id.action_delete:
                    deleteLocations();
                    return true;
                case R.id.action_select_all:
                    selectAllLocations();
                    return true;
                case R.id.action_cancel:
                    cancelLastActions();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate the menu for the CAB
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.contextual_menu_locations, menu);
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mLocationsAdapter.deselectAll();
            hideLocationSearch();
            mLocationsAdapter.setIsInEditMode(false);
            mActionMode = null;
            updateLocations();
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
    };

    private AdapterView.OnItemClickListener mLocationsItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            if (isInEditMode()) {
                mLocationsAdapter.toggleSelection(position);
            } else {
                Preferences.getInstance(LocationsActivity.this).setSelectedLocation(position);
                NavUtils.navigateUpFromSameTask(LocationsActivity.this);
            }
        }
    };

    private SearchView.OnQueryTextListener mOnQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            mLocationSearchClient.getLocations(new LocationSearchRequest(new LocationQuery(s), true));
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    };

    private ClientProgressListener mLocationSearchProgressListener = new ClientProgressListener() {
        @Override
        public void onStart(BaseClient client) {
            showSearchLoading();
        }

        @Override
        public void onFinish(BaseClient client) {
            hideSearchLoading();
        }
    };

    private AdapterView.OnItemSelectedListener mOnSearchResultSelectedListener =
            new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

            if (position > 0) {
                addLocation(mLocationResultsAdapter.getItem(position));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) { }
    };

    private ClientResultListener<LocationSearch> mLocationSearchResultListener =
            new ClientResultListener<LocationSearch>() {
        @Override
        public void onSuccess(BaseClient client, LocationSearch result) {
            displaySearchResults(result);
        }

        @Override
        public void onError(BaseClient client, String errorMessage) {
            showErrorDialog(errorMessage);
        }
    };

    private boolean hasResults(final LocationSearch locationSearch) {
        return locationSearch != null && locationSearch.getData() != null
                && locationSearch.getLocationList().size() > 0;
    }

    private void addLocation(String location) {
        if (!mLocationsAdapter.getLocations().contains(location)) {
            mLocationsAdapter.add(location);
            showToast(getString(R.string.location_added));
        }
        mListViewLocations.setSelection(mLocationsAdapter.getPosition(location));
        hideLocationSearch();
        mSearchViewLocation.setQuery("", false);
    }

    private void deleteLocations() {
        if (mLocationsAdapter.getSelectedCount() == 1) {
            showToast(getString(R.string.location_deleted));
        } else if (mLocationsAdapter.getSelectedCount() > 1) {
            showToast(getString(R.string.locations_deleted));
        }
        mLocationsAdapter.removeSelectedItems();
    }

    private void selectAllLocations() {
        mLocationsAdapter.selectAll();
    }

    private void cancelLastActions() {
        mWasActionCanceled = true;
        mActionMode.finish();
    }

    private void displaySearchResults(final LocationSearch locationSearch) {
        List<String> locationResults = new ArrayList<String>();

        if (hasResults(locationSearch)) {
            for (Location location : locationSearch.getLocationList()) {

                String fullLocation = getFullLocationName(location);

                if (!locationResults.contains(fullLocation)) {
                    locationResults.add(fullLocation);
                }
            }

            int resultCount = locationResults.size();
            if (resultCount == 1) {
                // Instantly add city to the city list
                addLocation(locationResults.get(0));
            } else {
                // Show results and let the user choose from the list
                locationResults.add(0, getString(R.string.choose_location));
                showResultsInSpinner(locationResults);
            }
        } else {
            // Show no results alert
            locationResults.add(getString(R.string.no_results));
            showResultsInSpinner(locationResults);
        }

    }

    /* Returns location name including area name and country */
    private String getFullLocationName(final Location location) {
        StringBuilder locationBuilder = new StringBuilder();
        locationBuilder.append(location.getAreaName()).append(", ").append(location.getCountry());
        return locationBuilder.toString();
    }

    private void showResultsInSpinner(final List<String> results) {
        mLocationResultsAdapter = new LocationResultsAdapter(LocationsActivity.this, results);
        mSpinnerSearchResults.setAdapter(mLocationResultsAdapter);
        mSpinnerSearchResults.performClick();
        mSpinnerSearchResults.setOnItemSelectedListener(mOnSearchResultSelectedListener);
    }

    private void updateLocations() {
        if (mWasActionCanceled) {
            mLocationsAdapter.setLocations(mLocationsBackup); // Restore locations
            mWasActionCanceled = false;
        } else {
            setResult(RESULT_OK); // Update local weather pager
            mLocationsBackup = new ArrayList<String>(mLocationsAdapter.getLocations());
            Preferences.getInstance(this).setUserLocations(mLocationsBackup);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(getString(R.string.title_activity_locations));

        mListViewLocations = (ListView) findViewById(R.id.list_view_locations);

        mLocations = Preferences.getInstance(this).getUserLocations();
        mLocationsBackup = new ArrayList<String>(mLocations);

        mLocationsAdapter = new LocationsAdapter(this, mLocations);
        mLocationsAdapter.setOnSelectionChangeListener(this);
        mListViewLocations.setAdapter(mLocationsAdapter);
        mListViewLocations.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mListViewLocations.setOnItemClickListener(mLocationsItemClickListener);
        mListViewLocations.setLongClickable(false);
        mListViewLocations.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideLocationSearch();
                return false;
            }
        });

        handleNetworkConnection(NetworkUtils.isConnected(this));
    }

    private void toggleLocationSearch() {
        if (isSearchVisible()) {
            hideLocationSearch();
        } else {
            showLocationSearch();
        }
    }

    private boolean isSearchVisible() {
        return mLayoutSearchLocation.getVisibility() == View.VISIBLE;
    }

    private void showLocationSearch() {
        mLayoutSearchLocation.setVisibility(View.VISIBLE);
        mSearchViewLocation.setQuery("", false);
        showInputMethod(mSearchViewLocation);
    }

    private void hideLocationSearch() {
        mLayoutSearchLocation.setVisibility(View.GONE);
    }

    private void showSearchLoading() {
        mProgressBarSearch.setVisibility(View.VISIBLE);
    }

    private void hideSearchLoading() {
        mProgressBarSearch.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_locations, menu);
        setupSearch();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_edit:
                editLocations();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void editLocations() {
        if (isInEditMode()) {
            return;
        }
        mActionMode = startActionMode(mMultiChoiceModeListener);
        mLocationsAdapter.setIsInEditMode(true);
        mActionMode.setTitle(getString(R.string.title_edit_locations));
    }

    @Override
    public void onSelectionChanged(int selectedCount) {
        if (isInEditMode()) {
            if (selectedCount > 0) {
                mActionMode.setTitle(String.valueOf(selectedCount) + " selected");
            } else {
                mActionMode.setTitle(getString(R.string.title_edit_locations));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationsAdapter.removeOnSelectionChangeListener();

    }

    private boolean isInEditMode() {
        return mActionMode != null;
    }

    private void setupSearch() {
        mLayoutSearchLocation = (LinearLayout) findViewById(R.id.layout_search_location);
        mSearchViewLocation = (SearchView) findViewById(R.id.search_view_location);
        mProgressBarSearch = (ProgressBar) findViewById(R.id.progress_bar_search);

        mSearchViewLocation.setIconifiedByDefault(false);
        mSearchViewLocation.setOnQueryTextListener(mOnQueryTextListener);

        mSpinnerSearchResults = (Spinner)
                mLayoutSearchLocation.findViewById(R.id.spinner_search_results);

        mLocationSearchClient = new LocationSearchClient(this);
        mLocationSearchClient.setProgressListener(mLocationSearchProgressListener);
        mLocationSearchClient.setResultListener(mLocationSearchResultListener);
    }

}
