package ca.xef6.firecamp.events;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import ca.xef6.firecamp.R;

/*
 * TodosOverviewActivity displays the existing todo items
 * in a list
 * 
 * You can create new ones via the ActionBar entry "Insert"
 * You can delete existing ones via a long press on the item
 */

public class EventsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DELETE_ID = Menu.FIRST + 1;
    // private Cursor cursor;
    private SimpleCursorAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	setHasOptionsMenu(true);
	// setContentView(R.layout.todo_list);
	this.getListView().setDividerHeight(2);
	fillData();
	registerForContextMenu(getListView());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	return inflater.inflate(R.layout.todo_list, container, false);
    }

    // Create the menu based on the XML defintion
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	inflater.inflate(R.menu.fragment_events, menu);
    }

    // Reaction to the menu selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case R.id.insert:
	    createTodo();
	    return true;
	}
	return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case DELETE_ID:
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    Uri uri = Uri.parse(EventsContentProvider.CONTENT_URI + "/" + info.id);
	    getActivity().getContentResolver().delete(uri, null, null);
	    fillData();
	    return true;
	}
	return super.onContextItemSelected(item);
    }

    private void createTodo() {
	Intent i = new Intent(getActivity() /* FIXME */, EventDetailActivity.class);
	startActivity(i);
    }

    // Opens the second activity if an entry is clicked
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
	super.onListItemClick(l, v, position, id);
	Intent i = new Intent(getActivity() /* FIXME */, EventDetailActivity.class);
	Uri todoUri = Uri.parse(EventsContentProvider.CONTENT_URI + "/" + id);
	i.putExtra(EventsContentProvider.CONTENT_ITEM_TYPE, todoUri);

	// Activity returns an result if called with startActivityForResult
	startActivity(i);
    }

    private void fillData() {

	// Fields from the database (projection)
	// Must include the _id column for the adapter to work
	String[] from = new String[] { EventsTable.COLUMN_IMAGE_URL };
	// Fields on the UI to which we map
	int[] to = new int[] { R.id.label };

	getLoaderManager().initLoader(0, null, this);
	adapter = new SimpleCursorAdapter(getActivity(), R.layout.todo_row, null, from, to, 0);

	setListAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	super.onCreateContextMenu(menu, v, menuInfo);
	menu.add(0, DELETE_ID, 0, R.string.menu_delete);
    }

    // Creates a new loader after the initLoader () call
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
	String[] projection = { EventsTable.COLUMN_ID, EventsTable.COLUMN_IMAGE_URL };
	// this, EventsContentProvider.CONTENT_URI, projection, null, null, null
	CursorLoader cursorLoader = new CursorLoader(getActivity(), EventsContentProvider.CONTENT_URI, projection, null, null, null);
	return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
	adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
	// data is not available anymore, delete reference
	adapter.swapCursor(null);
    }

}