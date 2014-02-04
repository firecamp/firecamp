package ca.xef6.firecamp.people;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;

public class PeopleFragment extends ListFragment implements OnCloseListener,
		OnQueryTextListener, LoaderCallbacks<Cursor> {

	private SimpleCursorAdapter adapter;
	private String currentFilter;
	private SearchView searchView;

	// --------------------------------------------------------------------------
	// Fragment methods.
	// --------------------------------------------------------------------------

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}

	// --------------------------------------------------------------------------
	// OnCloseListener methods.
	// --------------------------------------------------------------------------

	@Override
	public boolean onClose() {
		return false;
	}

	// --------------------------------------------------------------------------
	// OnQueryTextListener methods.
	// --------------------------------------------------------------------------

	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return true;
	}

	// --------------------------------------------------------------------------
	// LoaderCallbacks<Cursor> methods.
	// --------------------------------------------------------------------------

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

	}

}