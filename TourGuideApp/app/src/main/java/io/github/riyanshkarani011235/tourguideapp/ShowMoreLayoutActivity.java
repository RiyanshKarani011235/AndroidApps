package io.github.riyanshkarani011235.tourguideapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Attr;

public class ShowMoreLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more_layout);

        // get the Extras passed to the intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int imageResourceId = intent.getIntExtra("imageResourceId",0);
        String description = intent.getStringExtra("description");
        String timings = intent.getStringExtra("timings");

        // change the view attributes in the activity_show_more_layout
        ImageView image = (ImageView) findViewById(R.id.image);
        TextView nameTextField = (TextView) findViewById(R.id.name);
        TextView descriptionTextField = (TextView) findViewById(R.id.description_text);
        TextView timingsTextField = (TextView) findViewById(R.id.timings);

        image.setImageResource(imageResourceId);
        nameTextField.setText(name);
        descriptionTextField.setText(description);
        timingsTextField.setText("Timings : " + timings);

    }

}
