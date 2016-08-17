package io.github.riyanshkarani011235.habittrackerapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.provider.BaseColumns;
import android.provider.UserDictionary;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyDatabase database = new MyDatabase(getBaseContext());
        try {
            Thread.sleep(5000);
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (database.databaseAvailable == true) {
            database.insert("code", "for 10 hours");
            database.insert("sleep", "for 7 hours");
            database.insert("repeat", "forever");
            Log.i("database : ", database.toString()); // the toString method calls the read method
            database.update(1, "code", "for 13 hours");
            Log.i("database : ", database.toString());
            database.delete(MainActivity.this);
        }
    }
}