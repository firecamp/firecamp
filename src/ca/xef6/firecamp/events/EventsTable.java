package ca.xef6.firecamp.events;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EventsTable {

    // Database table
    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_END_TIME = "end_time";
    public static final String COLUMN_AUTHOR = "author";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table " + TABLE_EVENTS + "(" //
	    + COLUMN_ID + " integer primary key autoincrement, " //
	    + COLUMN_NAME + " text not null, " //
	    + COLUMN_START_TIME + " integer, " //
	    + COLUMN_END_TIME + "integer, " //
	    + COLUMN_IMAGE_URL + " text not null," //
	    + COLUMN_AUTHOR + " text not null);";

    public static void onCreate(SQLiteDatabase database) {
	database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
	Log.w(EventsTable.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
	database.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
	onCreate(database);
    }
}