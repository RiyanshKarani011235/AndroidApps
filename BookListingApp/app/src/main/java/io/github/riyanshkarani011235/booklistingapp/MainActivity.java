package io.github.riyanshkarani011235.booklistingapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText inputString = (EditText) findViewById(R.id.input_field);
                String searchString = formatToQueryCompatibleString(inputString.getText().toString());

                Intent intent = new Intent(MainActivity.this, BookListActivity.class);
                intent.putExtra("searchString", searchString);
                startActivity(intent);
            }
        });

    }

    private String formatToQueryCompatibleString(String string) {
        // removes spaces and replaces them with underscores in the
        // string, so that the string can be sent as a query string
        String returnString = "";
        char space = " ".charAt(0);
        for(int i=0; i<string.length(); i++) {
            char character = string.charAt(i);
            if(character == space) {
                returnString += "_";
            } else {
                returnString += character;
            }
        }
        return returnString;
    }
}
