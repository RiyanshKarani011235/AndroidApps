package io.github.riyanshkarani011235.tourguideapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ironstein on 30/06/16.
 */
public class AttractionsAdapter extends ArrayAdapter<Attraction> {

    public AttractionsAdapter(Activity context, ArrayList attractions) {
        super(context, 0, attractions);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // check if the existing view is being reused
        View viewAttractions = convertView;
        final AttractionViewHolder viewHolder;

        if(viewAttractions == null) {
            // not being reused, has to be initialized
            viewAttractions = LayoutInflater.from(getContext()).inflate(R.layout.card_layout, parent, false);

            // updating viewHolder
            viewHolder = new AttractionViewHolder();
            viewHolder.imageId = (ImageView) viewAttractions.findViewById(R.id.image);
            viewHolder.nameId = (TextView) viewAttractions.findViewById(R.id.name);
            viewHolder.shortDescriptionId = (TextView) viewAttractions.findViewById(R.id.short_description_text);
            viewHolder.seeMoreId = (TextView) viewAttractions.findViewById(R.id.see_more);
            viewHolder.getDirectionsId = (TextView) viewAttractions.findViewById(R.id.get_directions);
            viewAttractions.setTag(viewHolder);
        } else {
            viewHolder = (AttractionViewHolder) viewAttractions.getTag();
        }

        final Attraction attraction = getItem(position);
        viewHolder.imageId.setImageResource(attraction.getImageResourceId());
        viewHolder.nameId.setText(attraction.getName());
        viewHolder.shortDescriptionId.setText(attraction.getmShortDescription());

        viewHolder.seeMoreId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showMoreLayoutIntent = new Intent(getContext(), ShowMoreLayoutActivity.class);
                showMoreLayoutIntent.putExtra("name", attraction.getName());
                showMoreLayoutIntent.putExtra("imageResourceId", attraction.getImageResourceId());
                showMoreLayoutIntent.putExtra("description", attraction.getmDescription());
                showMoreLayoutIntent.putExtra("timings", attraction.getTimings());
                getContext().startActivity(showMoreLayoutIntent);
            }
        });

        viewHolder.getDirectionsId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse("geo:" + attraction.getLocation());
                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");
                if(mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
                    // Attempt to start an activity that can handle the Intent
                    getContext().startActivity(mapIntent);
                }
            }
        });

        return viewAttractions;

    }


}
