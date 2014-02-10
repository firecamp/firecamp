package ca.xef6.firecamp.events;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class EventsContentProvider extends ContentProvider {

	private EventsDatabaseHelper database;

	private static final int EVENTS = 10;
	private static final int EVENT_ID = 20;

	private static final String AUTHORITY = "ca.xef6.firecamp.events";

	private static final String BASE_PATH = "events";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH);

	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/events";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/event";

	private static final UriMatcher uriMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		uriMatcher.addURI(AUTHORITY, BASE_PATH, EVENTS);
		uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", EVENT_ID);
	}

	@Override
	public boolean onCreate() {
		database = new EventsDatabaseHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		// Using SQLiteQueryBuilder instead of query() method
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		// Check if the caller has requested a column which does not exists
		checkColumns(projection);

		// Set the table
		queryBuilder.setTables(EventsTable.TABLE_EVENTS);

		int uriType = uriMatcher.match(uri);
		switch (uriType) {
		case EVENTS:
			break;
		case EVENT_ID:
			queryBuilder.appendWhere(EventsTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		SQLiteDatabase db = database.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, null, null, sortOrder);
		// Make sure that potential listeners are getting notified
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = uriMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		long id = 0;
		switch (uriType) {
		case EVENTS:
			id = sqlDB.insert(EventsTable.TABLE_EVENTS, null, values);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(BASE_PATH + "/" + id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = uriMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		switch (uriType) {
		case EVENTS:
			rowsDeleted = sqlDB.delete(EventsTable.TABLE_EVENTS, selection,
					selectionArgs);
			break;
		case EVENT_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(EventsTable.TABLE_EVENTS,
						EventsTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(EventsTable.TABLE_EVENTS,
						EventsTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		int uriType = uriMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsUpdated = 0;
		switch (uriType) {
		case EVENTS:
			rowsUpdated = sqlDB.update(EventsTable.TABLE_EVENTS, values,
					selection, selectionArgs);
			break;
		case EVENT_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(EventsTable.TABLE_EVENTS, values,
						EventsTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(EventsTable.TABLE_EVENTS, values,
						EventsTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

	private void checkColumns(String[] projection) {
		String[] available = EventsTable.ALL_COLUMNS;
		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(
					Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(
					Arrays.asList(available));
			// Check if all columns which are requested are available
			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException(
						"Unknown columns in projection");
			}
		}
	}

}
