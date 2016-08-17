package io.github.riyanshkarani011235.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ironstein on 28/07/16.
 */
public class ProductsDatabase {

    ProductsDatabaseDbHelper mDbHelper;
    SQLiteDatabase db;

    boolean databaseAvailable = true;
    int rowId = 1;

    public ProductsDatabase(Context context) {
        mDbHelper = new ProductsDatabaseDbHelper(context);
        new GetWritableDatabase().execute();
    }

    private class GetWritableDatabase {

        public GetWritableDatabase() {}

        public void execute() {
            Log.i("GetWritableDatabase", "loading database ...");
            db = mDbHelper.getWritableDatabase();
            Log.i("myDatabase", db.toString());
            databaseAvailable = true;
            Log.i("GetWritableDatabase", "loading database complete");
        }

    }

    // database methods
    public void insert(String productName, String price, String quantityAvailable, String imageUri, String productQuantityOrdered) {
        ContentValues values = new ContentValues();
        int entryId = rowId;
        values.put(ProductsDatabaseContract.FeedEntry.COLUMN_NAME_ENTRY_ID, entryId);
        values.put(ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_NAME, productName);
        values.put(ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_PRICE, price);
        values.put(ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_QUANTITY_AVAILABLE, quantityAvailable);
        values.put(ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_IMAGE_URI, imageUri);
        values.put(ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_QUANTITY_ORDERED, productQuantityOrdered);
        long newRowId = db.insert(
                ProductsDatabaseContract.FeedEntry.TABLE_NAME,
                ProductsDatabaseContract.FeedEntry.COLUMN_NAME_NULLABLE,
                values);
        rowId += 1;
    }

    public Product readProduct(String productName) {
        String[] productNameArray = new String[1];
        productNameArray[0] = productName;
        String whereClause = ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_NAME + "=?";
        String[] projection = {
                ProductsDatabaseContract.FeedEntry.COLUMN_NAME_ENTRY_ID,
                ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_NAME,
                ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_PRICE,
                ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_QUANTITY_AVAILABLE,
                ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_IMAGE_URI,
                ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_QUANTITY_ORDERED
        };

        String sortOrder = ProductsDatabaseContract.FeedEntry.COLUMN_NAME_ENTRY_ID + " Asc";
        Cursor c = db.query(
                ProductsDatabaseContract.FeedEntry.TABLE_NAME,                  // the table to query
                projection,                                                     // the columns to return
                whereClause,                                                    // the columns for the WHERE clause
                productNameArray,                                               // the values for the WHERE clause
                null,                                                           // don't gorup the rows
                null,                                                           // don't filter the row groups
                sortOrder                                                       // sort order
        );

        String readEntryId = "";
        String readProductName = "";
        String readProductPrice = "";
        String readProductQuantity = "";
        String readProductImageUri = "";
        String readProductQuatityOrdered = "";

        String readString = "";
        while(c.moveToNext()) {
            readEntryId = c.getString(
                    c.getColumnIndexOrThrow(ProductsDatabaseContract.FeedEntry.COLUMN_NAME_ENTRY_ID)
            );
            readProductName = c.getString(
                    c.getColumnIndexOrThrow(ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_NAME)
            );
            readProductPrice = c.getString(
                    c.getColumnIndexOrThrow(ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_PRICE)
            );
            readProductQuantity = c.getString(
                    c.getColumnIndexOrThrow(ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_QUANTITY_AVAILABLE)
            );
            readProductImageUri = c.getString(
                    c.getColumnIndexOrThrow(ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_IMAGE_URI)
            );
            readProductQuatityOrdered = c.getString(
                    c.getColumnIndexOrThrow(ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_QUANTITY_ORDERED)
            );
            readString += readEntryId + ", " + readProductName + ", " + readProductPrice + ", " + readProductQuantity + ", " + readProductImageUri + "\n";
        }
        Log.i("readString", readString);
        Product returnProduct = new Product(readEntryId, readProductName, readProductPrice, readProductQuantity, readProductImageUri, readProductQuatityOrdered);
        return returnProduct;
    }

    public ArrayList<Product> readAllProducts() {
        String[] projection = {
                ProductsDatabaseContract.FeedEntry.COLUMN_NAME_ENTRY_ID,
                ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_NAME,
                ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_PRICE,
                ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_QUANTITY_AVAILABLE,
                ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_IMAGE_URI,
                ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_QUANTITY_ORDERED
        };

        String sortOrder = ProductsDatabaseContract.FeedEntry.COLUMN_NAME_ENTRY_ID + " Asc";
        Cursor c = db.query(
                ProductsDatabaseContract.FeedEntry.TABLE_NAME,                  // the table to query
                projection,                                                     // the columns to return
                null,                                               // the columns for the WHERE clause
                null,                                               // the values for the WHERE clause
                null,                                                           // don't gorup the rows
                null,                                                           // don't filter the row groups
                sortOrder                                                       // sort order
        );

        ArrayList<Product> returnValue = new ArrayList<>();
        String readString = "";
        while(c.moveToNext()) {
            String readEntryId = c.getString(
                    c.getColumnIndexOrThrow(ProductsDatabaseContract.FeedEntry.COLUMN_NAME_ENTRY_ID)
            );
            String readProductName = c.getString(
                    c.getColumnIndexOrThrow(ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_NAME)
            );
            String readProductPrice = c.getString(
                    c.getColumnIndexOrThrow(ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_PRICE)
            );
            String readProductQuantity = c.getString(
                    c.getColumnIndexOrThrow(ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_QUANTITY_AVAILABLE)
            );
            String readProductImageUri = c.getString(
                    c.getColumnIndexOrThrow(ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_IMAGE_URI)
            );
            String readProductQuantityOrdered = c.getString(
                    c.getColumnIndexOrThrow(ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_QUANTITY_ORDERED)
            );
            readString += readEntryId + ", " + readProductName + ", " + readProductPrice + ", " + readProductQuantity + ", " + readProductImageUri + "\n";
            Product tempProduct = new Product(readEntryId, readProductName, readProductPrice, readProductQuantity, readProductImageUri, readProductQuantityOrdered);
            returnValue.add(tempProduct);
        }
        return returnValue;
    }

    public void deleteProduct(String productName) {
        String executableString = "DELETE FROM " +
                ProductsDatabaseContract.FeedEntry.TABLE_NAME + " WHERE " +
                ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_NAME + "=\"" + productName + "\"";
        db.execSQL(executableString);
    }

    public void cleanDatabase() {
        db.execSQL(ProductsDatabaseContract.Methods.SQL_DELETE_ENTRIES);
        db.execSQL(ProductsDatabaseContract.Methods.SQL_CREATE_ENTRIES);
    }

    public String toString() {
        String returnString = "";
        ArrayList<Product> table = readAllProducts();
        for(int i=0; i<table.size(); i++) {
            returnString +=  table.get(i).toString() + "\n";
        }
        return returnString;
    }

}
