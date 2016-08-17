package io.github.riyanshkarani011235.booklistingapp;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by ironstein on 01/07/16.
 */
public class Book {
    String mTitle;
    ArrayList<String> mAuthors;
    String mPublisher;
    String mDescription;
    ArrayList<String> mImageLinks;
    String mLanguage;
    String mPreviewLink;
    Bitmap thumbnailImage;

    public Book(String title, ArrayList<String> authors, String pulisher, String description,
                ArrayList<String> imageLinks, String language, String previewLink) {
        mTitle = title;
        mAuthors = authors;
        mPublisher = pulisher;
        mDescription = description;
        mImageLinks = imageLinks;
        mLanguage = language;
        mPreviewLink = previewLink;
    }

    // ----------------- getter methods --------------------
    public String getTitle() {
        return mTitle;
    }

    public ArrayList<String> getAuthors() {
        return mAuthors;
    }

    public String getPublister() {
        return mPublisher;
    }

    public String getDescription() {
        return mDescription;
    }

    public ArrayList<String> getImageLinks() {
        return mImageLinks;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public String getmPreviewLink() {
        return mPreviewLink;
    }

    public void setThumbnailImage(Bitmap thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public Bitmap getThumbnailImage() {
        return thumbnailImage;
    }

    // -----------------------------------------------------

    public String toString() {
        String returnString = "";
        returnString += "Title : " + mTitle + "\n";
        returnString += "Authors : ";
        for(int i=0; i<mAuthors.size(); i++) {
            returnString += mAuthors.get(i) + ", ";
        }
        returnString += "\n";
        returnString += "Publisher : " + mPublisher + "\n";
        returnString += "Description : " + mDescription + "\n";
        returnString += "Image Links : ";
        for(int i=0; i<mImageLinks.size(); i++) {
            returnString += mImageLinks.get(i) + ", ";
        }
        returnString += "\n";
        returnString += "Language : " + mLanguage + "\n";
        returnString += "PreviewLink : " + mPreviewLink + "\n";
        returnString += "\n";

        return returnString;
    }

}
