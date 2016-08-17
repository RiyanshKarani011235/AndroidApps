package io.github.riyanshkarani011235.booklistingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BookDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_description);

        Intent intent = getIntent();
        ((TextView) findViewById(R.id.description)).setText(intent.getStringExtra("description"));
        ((TextView) findViewById(R.id.publisher)).setText(intent.getStringExtra("publisher"));
        ((TextView) findViewById(R.id.language)).setText(intent.getStringExtra("language"));
    }
}
