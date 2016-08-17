package io.github.riyanshkarani011235.inventoryapp;

import android.provider.BaseColumns;

/**
 * Created by ironstein on 28/07/16.
 */
public class ProductsDatabaseContract {

    public ProductsDatabaseContract() {}

    public static abstract class FeedEntry implements BaseColumns {

        public static final String TABLE_NAME = "products";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        public static final String COLUMN_NAME_PRODUCT_NAME = "product_name";
        public static final String COLUMN_NAME_PRODUCT_PRICE = "product_price";
        public static final String COLUMN_NAME_PRODUCT_QUANTITY_AVAILABLE = "product_quantity_available";
        public static final String COLUMN_NAME_PRODUCT_IMAGE_URI = "product_image_uri";
        public static final String COLUMN_NAME_PRODUCT_QUANTITY_ORDERED = "product_quantity_ordered";
        public static final String COLUMN_NAME_NULLABLE = "NULL";

    }

    public static abstract class Variables {
        public static final String TEXT_TYPE = " TEXT";
        public static final String COMM_SEP = ", ";
    }

    public static abstract class Methods {
        // create new table
        public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE "  + FeedEntry.TABLE_NAME + " (" +
            FeedEntry._ID + " INTEGER PRIMARY KEY" + Variables.COMM_SEP +
            FeedEntry.COLUMN_NAME_ENTRY_ID + Variables.TEXT_TYPE + Variables.COMM_SEP +
            FeedEntry.COLUMN_NAME_PRODUCT_NAME + Variables.TEXT_TYPE + Variables.COMM_SEP +
            FeedEntry.COLUMN_NAME_PRODUCT_PRICE + Variables.TEXT_TYPE + Variables.COMM_SEP +
            FeedEntry.COLUMN_NAME_PRODUCT_QUANTITY_AVAILABLE + Variables.TEXT_TYPE + Variables.COMM_SEP +
            FeedEntry.COLUMN_NAME_PRODUCT_IMAGE_URI + Variables.TEXT_TYPE + Variables.COMM_SEP +
            FeedEntry.COLUMN_NAME_PRODUCT_QUANTITY_ORDERED + Variables.TEXT_TYPE +
            ");";

        // delete an existing table
        public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    }

}
