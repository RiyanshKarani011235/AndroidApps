package io.github.riyanshkarani011235.tourguideapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class RestaurantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        ArrayList<Attraction> restaurants = new ArrayList<Attraction>();

        restaurants.add(new Attraction(
                getResources().getString(R.string.restaurants_activity_1_name),
                R.drawable.restaurants_activity_1_image,
                getResources().getString(R.string.restaurants_activity_1_location),
                getResources().getString(R.string.restaurants_activity_1_shortDescription),
                getResources().getString(R.string.restaurants_activity_1_description),
                getResources().getString(R.string.restaurants_activity_1_timings)
        ));

        restaurants.add(new Attraction(
                getResources().getString(R.string.restaurants_activity_2_name),
                R.drawable.restaurants_activity_2_image,
                getResources().getString(R.string.restaurants_activity_2_location),
                getResources().getString(R.string.restaurants_activity_2_shortDescription),
                getResources().getString(R.string.restaurants_activity_2_description),
                getResources().getString(R.string.restaurants_activity_2_timings)
        ));

        restaurants.add(new Attraction(
                getResources().getString(R.string.restaurants_activity_3_name),
                R.drawable.restaurants_activity_3_image,
                getResources().getString(R.string.restaurants_activity_3_location),
                getResources().getString(R.string.restaurants_activity_3_shortDescription),
                getResources().getString(R.string.restaurants_activity_3_description),
                getResources().getString(R.string.restaurants_activity_3_timings)
        ));

        restaurants.add(new Attraction(
                getResources().getString(R.string.restaurants_activity_4_name),
                R.drawable.restaurants_activity_4_image,
                getResources().getString(R.string.restaurants_activity_4_location),
                getResources().getString(R.string.restaurants_activity_4_shortDescription),
                getResources().getString(R.string.restaurants_activity_4_description),
                getResources().getString(R.string.restaurants_activity_4_timings)
        ));

        AttractionsAdapter restaurantsAdapter = new AttractionsAdapter(this, restaurants);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(restaurantsAdapter);

    }
}
