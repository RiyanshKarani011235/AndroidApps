package io.github.riyanshkarani011235.habittrackerapp;

import android.provider.BaseColumns;

/**
 * Created by ironstein on 06/07/16.
 */

/*
     * One of the main principles of SQL databases is the schema: a formal
     * declaration of how the database is organized. The schema is reflected
     * in the SQL statements that you use to create your database. You may
     * find it helpful to create a companion class, known as a contract class,
     * which explicitly specifies the layout of your schema in a systematic
     * and self-documenting way.
     *
     * A contract class is a container for constants that define names for
     * URIs, tables, and columns. The contract class allows you to use the
     * same constants across all the other classes in the same package.
     * This lets you change a column name in one place and have it propagate
     * throughout your code.
     */

public final class FeedReaderContract {

    // to prevent someone from accidentally instantiating the contract
    // class, give it an empty constructor
    public FeedReaderContract() {}

    // Inner class that defines the table contents
    public static abstract class FeedEntry implements BaseColumns {
        /*
         * By implementing the BaseColumns interface, your inner class can
         * inherit a primary key field called _ID that some Android classes
         * such as cursor adaptors will expect it to have. It's not required,
         * but this can help your database work harmoniously with the Android
         * framework.
         */
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
        public static final String COLUMN_NAME_NULLABLE = "NULL";
    }

    public static abstract class variables {
        public static final String TEXT_TYPE = " TEXT";
        public static final String COMM_SEP = ", ";
    }

    public static abstract class Methods {

        // create a new table
        public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " ("  +
            FeedEntry._ID + " INTEGER PRIMARY KEY" + variables.COMM_SEP +
            FeedEntry.COLUMN_NAME_ENTRY_ID + variables.TEXT_TYPE + variables.COMM_SEP +
            FeedEntry.COLUMN_NAME_TITLE + variables.TEXT_TYPE + variables.COMM_SEP +
            FeedEntry.COLUMN_NAME_SUBTITLE + variables.TEXT_TYPE +
            ");";

        // delete an existing table
        public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
    }

}