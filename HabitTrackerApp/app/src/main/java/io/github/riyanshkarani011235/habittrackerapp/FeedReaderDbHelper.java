package io.github.riyanshkarani011235.habittrackerapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ironstein on 06/07/16.
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper{

    // If you change the database schema, you must increment the database version
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public FeedReaderDbHelper(Context context) {
        // Create a helper object to create, open, and/or manage a database.
        // This method always returns very quickly. The database is not actually
        // created or opened until one of getWritableDatabase() or
        // getReadableDatabase() is called.

        // SqLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate (SQLiteDatabase db) {
        db.execSQL(FeedReaderContract.Methods.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy
        // is to simply discard the data and start over
        db.execSQL(FeedReaderContract.Methods.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public SQLiteDatabase getWritableDatabase() {
        // Create and/or open a database that will be used for reading and writing.

        // The first time this is called, the database will be opened and
        // onCreate(SQLiteDatabase), onUpgrade(SQLiteDatabase, int, int) and/or
        // onOpen(SQLiteDatabase) will be called.

        // Once opened successfully, the database is cached, so you can call this
        // method every time you need to write to the database.
        // (Make sure to call close() when you no longer need the database.)
        // Errors such as bad permissions or a full disk may cause this method to fail,
        // but future attempts may succeed if the problem is fixed.

        // Database upgrade may take a long time, you should not call this method from
        // the application main thread, including from ContentProvider.onCreate().
        return super.getWritableDatabase();
    }

    public SQLiteDatabase getReadableDatabase() {
        // Create and/or open a database. This will be the same object returned by
        // getWritableDatabase() unless some problem, such as a full disk, requires
        // the database to be opened read-only.
        return super.getReadableDatabase();
    }

    public void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

}
