package ca.xef6.firecamp.events;

import android.database.sqlite.SQLiteDatabase;

public class EventsTable {

    public static final String TABLE_EVENTS = "events";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_IMAGE_URL = "image";

    public static final String[] ALL_COLUMNS = { EventsTable.COLUMN_ID,
	    EventsTable.COLUMN_NAME, EventsTable.COLUMN_AUTHOR,
	    EventsTable.COLUMN_DESCRIPTION, EventsTable.COLUMN_DATE,
	    EventsTable.COLUMN_TIME, EventsTable.COLUMN_IMAGE_URL };

    public static final String[] ALL_COLUMNS_NO_ID = { EventsTable.COLUMN_NAME,
	    EventsTable.COLUMN_AUTHOR, EventsTable.COLUMN_DESCRIPTION,
	    EventsTable.COLUMN_DATE, EventsTable.COLUMN_TIME,
	    EventsTable.COLUMN_IMAGE_URL };

    private static final String DATABASE_CREATE = "create table " //
	    + TABLE_EVENTS + "(" //
	    + COLUMN_ID + " integer primary key autoincrement, " //
	    + COLUMN_NAME + " text not null, " //
	    + COLUMN_AUTHOR + " text, " //
	    + COLUMN_DESCRIPTION + " text not null, " //
	    + COLUMN_DATE + " text not null, " //
	    + COLUMN_TIME + " text not null, " //
	    + COLUMN_IMAGE_URL + " text not null);"; //

    public static void onCreate(SQLiteDatabase db) {
	db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion,
	    int newVersion) {
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
	onCreate(db);
    }
}