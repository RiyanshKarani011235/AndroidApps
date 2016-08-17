package io.github.riyanshkarani011235.inventoryapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ProductsDatabase db;
    public static ListView listView;
    private TextView noProductsTextView;
    public static ArrayList<Product> products;
    private static Activity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        mainActivity = MainActivity.this;

        db = new ProductsDatabase(MainActivity.this);
//        db.cleanDatabase();
        products = db.readAllProducts();

        listView = (ListView) findViewById(R.id.list_view);
        noProductsTextView = (TextView) findViewById(R.id.no_products_text_view);

        Button addProductButton = (Button) findViewById(R.id.add_product_button);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext() ,AddProductActivity.class);
                startActivity(intent);
            }
        });
        toggleView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onRestoreInstanceState", "called");
        listView.invalidate();
        toggleView();
    }

    public void toggleView() {
        products = db.readAllProducts();
        listView.setAdapter(new ProductsListAdapter(this, products));
        if(products.size() == 0) {
            listView.setVisibility(View.INVISIBLE);
            noProductsTextView.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            noProductsTextView.setVisibility(View.INVISIBLE);
        }
    }

    public static void refreshListView() {
        listView.invalidate();
        listView.setAdapter(new ProductsListAdapter(mainActivity, db.readAllProducts()));
    }

}
