package io.github.riyanshkarani011235.booklistingapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ironstein on 01/07/16.
 */
public class BooksAdapter extends ArrayAdapter<Book>{

    static class ViewHolder {
        ImageView thumbnailImage;
        TextView title;
        TextView authors;
        TextView showMore;
        TextView preview;
    }

    public BooksAdapter(Activity context, ArrayList books) {
        super(context, 0, books);
        Log.i("BooksAdapter", "creating new one");
    }

    ViewHolder viewHolder;

    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("BooksAdapter.getView", "called");
        View bookView = convertView;

        if(bookView == null) {
            // inflate new View
            bookView = LayoutInflater.from(getContext()).inflate(R.layout.book_card_layout, parent, false);

            // initialize ViewHolder
            viewHolder = new ViewHolder();
            viewHolder.thumbnailImage = (ImageView) bookView.findViewById(R.id.thumbnail_image);
            viewHolder.title = (TextView) bookView.findViewById(R.id.title);
            viewHolder.authors = (TextView) bookView.findViewById(R.id.authors);
            viewHolder.showMore = (TextView) bookView.findViewById(R.id.show_more);
            viewHolder.preview = (TextView) bookView.findViewById(R.id.preview);
            bookView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) bookView.getTag();
        }

        Log.i("", "setting viewHolder complete");

        final Book book = getItem(position);
        viewHolder.thumbnailImage.setImageBitmap(book.getThumbnailImage());
        viewHolder.title.setText(book.getTitle());
        String authors = "";
        for(int i=0; i<book.getAuthors().size(); i++) {
            authors += book.getAuthors().get(i);
            if(i != book.getAuthors().size() - 1) {
                authors += ", ";
            }
        }
        viewHolder.authors.setText(authors);
        viewHolder.preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(book.getmPreviewLink()));
                getContext().startActivity(browserIntent);
            }
        });

        viewHolder.showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BookDescriptionActivity.class);
                intent.putExtra("description", book.getDescription());
                intent.putExtra("publisher", book.getPublister());
                intent.putExtra("language", book.getLanguage());
                getContext().startActivity(intent);
            }
        });

        return bookView;
    }
}
