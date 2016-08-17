package io.github.riyanshkarani011235.booklistingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {

    private ArrayList<Book> books = new ArrayList<Book>();
    private io.github.riyanshkarani011235.booklistingapp.BookListActivity this_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String searchString = intent.getStringExtra("searchString");
        String stringUrl = "https://www.googleapis.com/books/v1/volumes?q=" + searchString;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
        } else {
            Toast.makeText(this, "No network connection available", Toast.LENGTH_SHORT).show();
            finish();
        }
        this_ = this;
        new DownloadWebpageTask().execute(stringUrl);
        setContentView(R.layout.loading_layout);
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, ArrayList<Boolean>> {

        @Override
        protected ArrayList<Boolean> doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            String webPageJson;
            ArrayList<Boolean> returnValue = new ArrayList<Boolean>();

            try {
                webPageJson = downloadUrl(urls[0]);
            } catch (IOException e) {
                Log.i("DownlaodWebpageTask", "exception caught");
                return returnValue;
            }

            Log.i("DownloadWebpageTak", "parsing json");
            try {
                JSONObject reader = new JSONObject(webPageJson);
                JSONArray items = new JSONArray(reader.getString("items"));
                populateBooksListArray(items);
            } catch (org.json.JSONException e) {
                Log.e("JSON parse error", e.toString());
                return returnValue;
            } finally {

                for (int i = 0; i < books.size(); i++) {
                    InputStream inputStream;
                    URL url;

                    // set up connection
                    try {
                        url = new URL(books.get(i).getImageLinks().get(0));
                        try {
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setReadTimeout(10000 /* milliseconds */);
                            connection.setConnectTimeout(15000 /* milliseconds */);
                            try {
                                connection.setRequestMethod("GET");
                                connection.setDoInput(true);

                                // start the query
                                try {
                                    connection.connect(); // if connection is not established, throws IOException
                                    int response = connection.getResponseCode();
                                    Log.i("resopnse code is", "" + response);
                                    inputStream = connection.getInputStream();
                                } catch (java.io.IOException e) {
                                    Log.e("error connecting", e.toString());
                                    returnValue.add(false);
                                    continue;
                                }
                            } catch (java.net.ProtocolException e) {
                                Log.e("error set requestMethod", e.toString());
                                returnValue.add(false);
                                continue;
                            }
                        } catch (java.io.IOException e) {
                            Log.e("url open conn. error", e.toString());
                            returnValue.add(false);
                            continue;
                        }

                    } catch (java.net.MalformedURLException e) {
                        Log.e("error downl. thumbnail", e.toString());
                        returnValue.add(false);
                        continue;
                    }
                    Bitmap bitmap = getBitmapFromInputStream(inputStream);
                    returnValue.add(true);
                    books.get(i).setThumbnailImage(bitmap);
                }

            }
            return returnValue;
        }


        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<Boolean> result) {

            if(books.size() == 0) {
                setContentView(R.layout.no_content_found_layout);
            } else {
                setContentView(R.layout.activity_book_list);
                BooksAdapter booksAdapter = new BooksAdapter(this_, books);
                ListView listView = (ListView) findViewById(R.id.list_view);
                listView.setAdapter(booksAdapter);
            }
        }

    }

    private String downloadUrl(String inputUrl) throws IOException {
        InputStream inputStream = null;
        URL url;
        String returnString = "";
        try {
            url = new URL(inputUrl);
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000 /* milliseconds */);
                connection.setConnectTimeout(15000 /* milliseconds */);
                try {
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);

                    // start the query
                    try {
                        connection.connect(); // if connection is not established, throws IOException
                        int response = connection.getResponseCode();
                        Log.i("resopnse code is", "" + response);
                        inputStream = connection.getInputStream();
                    } catch (java.io.IOException e) {
                        Log.i("error connecting", e.toString());
                    }
                } catch (java.net.ProtocolException e) {
                    Log.i("error set requestMethod", e.toString());
                }
            } catch (java.io.IOException e) {
                Log.i("url open conn. error", e.toString());
            }
            Log.i("downloadUrl", "successful");

        } catch (java.net.MalformedURLException e) {
            Log.i("error downl. thumbnail", e.toString());
        } finally {
            // make sure that InputStream is closed after the app finishes using it
            try {
                returnString = getStringFromInputStream(inputStream);
            } catch (IOException e) {
                Log.e("getStringFrom ...", e.toString());
            }

            if(inputStream != null) {
                inputStream.close();
            }
            return returnString;
        }
    }

    public String getStringFromInputStream(InputStream inputStream) throws IOException{
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            if(br != null) {
                br.close();
            }
        }

        return sb.toString();
    }

    private Bitmap getBitmapFromInputStream(InputStream inputStream) {
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }

    private void populateBooksListArray(JSONArray booksInfoArray) {
        String textString = "";
        Log.i("populateBooksListArray", "STARTING");
        for (int i=0; i<booksInfoArray.length(); i++) {

            JSONObject item;
            String title;
            ArrayList<String> authors = new ArrayList<String>();
            String publisher;
            String description;
            ArrayList<String> imageLinks = new ArrayList<String>();
            String language;
            String previewLink;

            try {
                item = booksInfoArray.getJSONObject(i);
                item = item.getJSONObject("volumeInfo");
            } catch (org.json.JSONException e) {
                Log.e("populateBooksListArray",e.toString());
                return;
            }

            // title
            try {
                title = item.getString("title");
            } catch (org.json.JSONException e) {
                Log.e("error fetching title",e.toString());
                title = "not available";
            }

            // authors

            try {
                JSONArray authorsJsonArray = item.getJSONArray("authors");
//                Log.i("authorsJsonArray", authorsJsonArray.toString());
                for(int j=0; j<authorsJsonArray.length(); j++) {
                    authors.add(authorsJsonArray.getString(j));
                }
            } catch (org.json.JSONException e) {
                Log.e("error fetching authors",e.toString());
                authors.add("not available");
            }

            // publisher
            try {
                publisher = item.getString("publisher");
            } catch (org.json.JSONException e) {
                Log.e("publisher",e.toString());
                publisher = "not available";
            }

            // description
            try {
                description = item.getString("description");
            } catch (org.json.JSONException e) {
                Log.e("description",e.toString());
                description = "not available";
            }

            // imageLinks
            try {
                JSONObject imageLinkJsonArray = item.getJSONObject("imageLinks");
                imageLinks.add(imageLinkJsonArray.getString("smallThumbnail"));
                imageLinks.add(imageLinkJsonArray.getString("thumbnail"));
            } catch (org.json.JSONException e) {
                Log.e("imageLinks",e.toString());
                imageLinks.add("not available");
            }

            // language
            try {
                language = item.getString("language");
            } catch (org.json.JSONException e) {
                Log.e("language",e.toString());
                language = "not available";
            }

            // previewLink
            try {
                previewLink = item.getString("previewLink");
            } catch (org.json.JSONException e) {
                Log.e("previewLink",e.toString());
                previewLink = "not available";
            }

            Book book = new Book(title, authors, publisher, description, imageLinks, language, previewLink);
            books.add(book);
            textString += book.toString();
        }
        Log.i("populateBooksListArray","DONE");
    }
}
