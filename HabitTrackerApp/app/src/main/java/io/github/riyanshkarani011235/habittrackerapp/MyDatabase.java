package io.github.riyanshkarani011235.habittrackerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.net.ProtocolException;
import java.util.ArrayList;

/**
 * Created by ironstein on 27/07/16.
 */
public class MyDatabase {

    SQLiteDatabase db;
    FeedReaderDbHelper mDbHelper;
    int rowId = 1;
    boolean databaseAvailable = false;

    public MyDatabase(Context context) {
        mDbHelper = new FeedReaderDbHelper(context);
        new GetWritableDatabase().execute();
    }

    public void insert(String title, String subtitle) {
        ContentValues values = new ContentValues();
        int entryId = rowId;
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID, entryId);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);
        long newRowId = db.insert(
            FeedReaderContract.FeedEntry.TABLE_NAME,
            FeedReaderContract.FeedEntry.COLUMN_NAME_NULLABLE,
            values);
        rowId += 1;
    }

    public Cursor read() {
        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
        };

        String sortOrder = FeedReaderContract.FeedEntry._ID + " Asc";
        Cursor c = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,    // the table to query
                projection,                                 // the columns to return
                null,                                       // the columns for the WHERE clause
                null,                                       // the values for the WHERE clause
                null,                                       // don't gorup the rows
                null,                                       // don't filter the row groups
                sortOrder                                   // sort order
        );
        return c;
    }

    public void delete(Context context) {
        db.execSQL(FeedReaderContract.Methods.SQL_DELETE_ENTRIES);
        mDbHelper.deleteDatabase(context);
        databaseAvailable = false;
    }

    // update method when only title is provided
    public void update(int entryId, String title) {
        String executableString = "UPDATE " + FeedReaderContract.FeedEntry.TABLE_NAME;
        executableString += " SET " + FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + "=" + "\"" + title + "\"";
        executableString += " WHERE " + FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID + "=" + entryId;
        Log.i("update", executableString);
        db.execSQL(executableString);
    }

    // update method when both title and subtitle are provided
    public void update(int entryId, String title, String subtitle) {
        String executableString = "UPDATE " + FeedReaderContract.FeedEntry.TABLE_NAME;
        executableString += " SET " + FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + "=" + "\"" + title + "\"";
        executableString += FeedReaderContract.variables.COMM_SEP;
        executableString += FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + "=" + "\"" + subtitle + "\"";
        executableString += " WHERE " + FeedReaderContract.FeedEntry._ID + "=" + entryId;
        Log.i("update", executableString);
        db.execSQL(executableString);
    }

    public String toString() {
        Cursor c = read();
        String returnString = "";
        while(c.moveToNext()) {
            long _Id = c.getLong(
                    c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID)   // get the column index for the ID column
            );
            String entryId = c.getString(
                    c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID)
            );
            String title = c.getString(
                    c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE)
            );
            String subtitle = c.getString(
                    c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE)
            );
            returnString += "element " + c.getPosition() + _Id + ", " + entryId + ", " + title + ", " + subtitle + "\n";
        }
        return returnString;
    }

    public void close() {
        mDbHelper.close();
        db.close();
    }

    private class GetWritableDatabase extends AsyncTask<String, Void, Integer> {
        // a class to execute the FeedReaderDbHelper.getWriteableDatabase
        // since it is recommended that this method be executed in a separate
        // thread (in android documentation)
        @Override
        protected Integer doInBackground(String... urls) {
            Log.i("GetWritableDatabase", "loading database ...");
            db = mDbHelper.getWritableDatabase();
            Log.i("myDatabase", db.toString());
            db.execSQL(FeedReaderContract.Methods.SQL_DELETE_ENTRIES);
            db.execSQL(FeedReaderContract.Methods.SQL_CREATE_ENTRIES);
            databaseAvailable = true;
            Log.i("GetWritableDatabase", "loading database complete");
            return new Integer(0);
        }

    }

}
