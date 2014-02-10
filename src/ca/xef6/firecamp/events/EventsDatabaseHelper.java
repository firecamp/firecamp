package ca.xef6.firecamp.events;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventsDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "events.db";
	private static final int DATABASE_VERSION = 5;

	public EventsDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		EventsTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		EventsTable.onUpgrade(db, oldVersion, newVersion);
	}

}