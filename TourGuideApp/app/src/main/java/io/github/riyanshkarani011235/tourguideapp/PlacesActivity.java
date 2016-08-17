package io.github.riyanshkarani011235.tourguideapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class PlacesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        ArrayList<Attraction> places = new ArrayList<Attraction>();
        places.add(new Attraction(
                getResources().getString(R.string.places_activity_1_name),
                R.drawable.places_activity_1_image,
                getResources().getString(R.string.places_activity_1_location),
                getResources().getString(R.string.places_activity_1_shortDescription),
                getResources().getString(R.string.places_activity_1_description),
                getResources().getString(R.string.places_activity_1_timings)
        ));

        places.add(new Attraction(
                getResources().getString(R.string.places_activity_2_name),
                R.drawable.places_activity_2_image,
                getResources().getString(R.string.places_activity_2_location),
                getResources().getString(R.string.places_activity_2_shortDescription),
                getResources().getString(R.string.places_activity_2_description),
                getResources().getString(R.string.places_activity_2_timings)
        ));

        places.add(new Attraction(
                getResources().getString(R.string.places_activity_3_name),
                R.drawable.places_activity_3_image,
                getResources().getString(R.string.places_activity_3_location),
                getResources().getString(R.string.places_activity_3_shortDescription),
                getResources().getString(R.string.places_activity_3_description),
                getResources().getString(R.string.places_activity_3_timings)
        ));
//
        places.add(new Attraction(
                getResources().getString(R.string.places_activity_4_name),
                R.drawable.places_activity_4_image,
                getResources().getString(R.string.places_activity_4_location),
                getResources().getString(R.string.places_activity_4_shortDescription),
                getResources().getString(R.string.places_activity_4_description),
                getResources().getString(R.string.places_activity_4_timings)
        ));

        AttractionsAdapter placesAdapter = new AttractionsAdapter(this, places);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(placesAdapter);

    }
}
