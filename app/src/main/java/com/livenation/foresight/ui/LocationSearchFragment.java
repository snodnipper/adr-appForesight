package com.livenation.foresight.ui;

import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.livenation.foresight.R;
import com.livenation.foresight.formatters.AddressFormatter;
import com.livenation.foresight.functional.OnErrors;
import java8.util.Optional;
import com.livenation.foresight.graph.presenters.GeocodePresenter;
import com.livenation.foresight.graph.presenters.PreferencesPresenter;
import com.livenation.foresight.service.model.Coordinates;
import com.livenation.foresight.util.InjectLayout;
import com.livenation.foresight.util.InjectionListFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnEditorAction;
import rx.Observable;

import static rx.android.observables.AndroidObservable.bindFragment;

@InjectLayout(R.layout.fragment_location_search)
public class LocationSearchFragment extends InjectionListFragment {
    @Inject GeocodePresenter presenter;
    @Inject PreferencesPresenter preferences;

    @InjectView(R.id.fragment_location_search_field) EditText searchField;

    private ArrayAdapter<Address> resultsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.resultsAdapter = new ResultsAdapter(getActivity());
        setListAdapter(resultsAdapter);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Observable<String> name = bindFragment(this, presenter.name);
        name.subscribe(searchField::setHint);
    }

    //region Searching

    private void startedLoading() {
        searchField.setEnabled(false);
        resultsAdapter.clear();
    }

    private void finishedLoading() {
        searchField.setEnabled(true);
    }

    private void runQuery(@NonNull String query) {
        if (TextUtils.isEmpty(query)) {
            resultsAdapter.clear();
        } else {
            startedLoading();

            Observable<List<Address>> results = bindFragment(this, presenter.runQuery(query));
            results.subscribe(resultsAdapter::addAll,
                              OnErrors.showDialogFrom(getFragmentManager()),
                              this::finishedLoading);
        }
    }

    //endregion


    //region Listeners

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Address location = resultsAdapter.getItem(position);
        preferences.setSavedManualLocation(Optional.of(new Coordinates(location.getLatitude(), location.getLongitude())));
    }

    @OnEditorAction(R.id.fragment_location_search_field)
    @SuppressWarnings("UnusedDeclaration")
    public boolean onSearchFieldEditorAction(TextView textView, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            runQuery(textView.getText().toString());
            return true;
        }

        return false;
    }

    //endregion


    private class ResultsAdapter extends ArrayAdapter<Address> {
        private ResultsAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_1);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView text = (TextView) super.getView(position, convertView, parent);

            Address location = getItem(position);
            text.setText(AddressFormatter.format(location));

            return text;
        }
    }
}
