package io.github.riyanshkarani011235.inventoryapp;

/**
 * Created by ironstein on 27/07/16.
 */
public class Product {

    public String productEntryId;
    public String productName;
    public String productPrice;
    public String productQuantityAvailable;
    public String productImageUri;
    public String productQuantityOrdered;

    public Product(String productEntryId, String productName, String productPrice, String productQuantityAvailable, String productImageUri, String productQuantityOrdered) {
        this.productEntryId = productEntryId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantityAvailable = productQuantityAvailable;
        this.productImageUri = productImageUri;
        this.productQuantityOrdered = productQuantityOrdered;
    }

    public String toString() {
        String returnString = "";
        returnString += "productEntryId : " + productEntryId + ", ";
        returnString += "productName : " + productName + ", ";
        returnString += "productPrice : " + productPrice + ", ";
        returnString += "productQuantityAvailable : " + productQuantityAvailable + ", ";
        returnString += "productImageUri : " + productImageUri + ", ";
        returnString += "productQuantityOrdered : " + productQuantityOrdered;
        return returnString;
    }

    ////////////////
    // productName
    ////////////////
    public void setProductName(String productName) {
        String executableString = "UPDATE " + ProductsDatabaseContract.FeedEntry.TABLE_NAME +
                " SET " + ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_NAME + "=" + productName +
                " WHERE " + ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_NAME + "=\"" + this.productName + "\"";
        MainActivity.db.db.execSQL(executableString);
        this.productName = productName;
    }

    public String getProductName() {
        return this.productName;
    }

    ///////////////////////////////
    // getProductQuantityAvailable
    ///////////////////////////////
    public int getProductQuantityAvailable() {
        return Integer.parseInt(this.productQuantityAvailable);
    }

    public void setProductQuantityAvailable(int productQuantityAvailable) {
        if(productQuantityAvailable >= 0) {
            String executableString = "UPDATE " + ProductsDatabaseContract.FeedEntry.TABLE_NAME +
                    " SET " + ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_QUANTITY_AVAILABLE + "=" + productQuantityAvailable +
                    " WHERE " + ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_NAME + "=\"" + this.productName + "\"";
            MainActivity.db.db.execSQL(executableString);
            this.productQuantityAvailable = "" + productQuantityAvailable;
        }
    }

    ///////////////////
    // productPrice
    //////////////////
    public int getProductPrice() {
        return Integer.parseInt(this.productPrice);
    }

    ////////////////////
    // productImageUri
    ///////////////////
    public String getProductImageUri() {
        return this.productImageUri;
    }

    ///////////////////////////
    // productQuantityOrdered
    //////////////////////////
    public void setProductQuantityOrdered(int productQuantityOrdered) {
        if(productQuantityOrdered >= 0) {
            String executableString = "UPDATE " + ProductsDatabaseContract.FeedEntry.TABLE_NAME +
                    " SET " + ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_QUANTITY_ORDERED + "=" + productQuantityOrdered +
                    " WHERE " + ProductsDatabaseContract.FeedEntry.COLUMN_NAME_PRODUCT_NAME + "=\"" + this.productName + "\"";
            MainActivity.db.db.execSQL(executableString);
            this.productQuantityOrdered = "" + productQuantityOrdered;
        }
    }

    public int getProductQuantityOrdered() {
        return Integer.parseInt(this.productQuantityOrdered);
    }
}
