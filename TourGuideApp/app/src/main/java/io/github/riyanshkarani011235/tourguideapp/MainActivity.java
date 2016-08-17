package io.github.riyanshkarani011235.tourguideapp;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // parent view for both places card view and food card view
        LinearLayout listView = (LinearLayout) findViewById(R.id.list_view);

        // initializing main_activity_card_layout for places card
        LinearLayout placesView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.main_activity_card_layout, listView, false);
        ImageView placesView_imageView = (ImageView) placesView.findViewById(R.id.image);
        placesView_imageView.setImageResource(R.drawable.places);
        TextView placesView_textView = (TextView) placesView.findViewById(R.id.name);
        placesView_textView.setText(R.string.activity_main_places_text);

        TextView placesView_explore = (TextView) placesView.findViewById(R.id.explore);
        placesView_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent placesIntent = new Intent(MainActivity.this, PlacesActivity.class);
                startActivity(placesIntent);
            }
        });

        // initializing main_activity_card_layout for food card
        LinearLayout foodView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.main_activity_card_layout, listView, false);
        ImageView foodView_imageView = (ImageView) foodView.findViewById(R.id.image);
        foodView_imageView.setImageResource(R.drawable.food);
        TextView foodView_textView = (TextView) foodView.findViewById(R.id.name);
        foodView_textView.setText(R.string.activity_main_food_text);

        TextView foodView_explore = (TextView) foodView.findViewById(R.id.explore);
        foodView_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent restaurantsIntent = new Intent(MainActivity.this, RestaurantsActivity.class);
                startActivity(restaurantsIntent);
            }
        });

        listView.addView(placesView);
        listView.addView(foodView);

    }
}
