package io.github.riyanshkarani011235.inventoryapp;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.app.DownloadManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.jar.Manifest;

public class AddProductActivity extends AppCompatActivity implements TakePhotoDialogFragment.DialogListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_PHOTO_FROM_GALLERY = 2;
    Uri photoUri = null;
    EditText productNameEditText;
    EditText priceEditText;
    EditText quantityAvailableEditText;

    // permission request codes
    static final int CAMERA_PERMISSION_REQUEST_CODE = 10;
    static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        productNameEditText = (EditText) findViewById(R.id.product_name);
        priceEditText = (EditText) findViewById(R.id.price);
//        quantityAvailableEditText = (EditText) findViewById(R.id.quantity_available);

        Button takePhotoButton = (Button) findViewById(R.id.take_photo_button);
        Button submitButton = (Button) findViewById(R.id.submit_button);

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePhotoDialogFragment newFragment = new TakePhotoDialogFragment();
                newFragment.show(getFragmentManager(), "takePhotoDialogFragment");
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = productNameEditText.getText().toString();
                String price = priceEditText.getText().toString();
//                String quantityAvailabe = quantityAvailableEditText.getText().toString();
                String quantityAvailabe = "0";
                if(productName.equals("")) {
                    Toast.makeText(getApplicationContext(),"Product name can not be empty",Toast.LENGTH_SHORT).show();
                } else if(price.equals("")) {
                    Toast.makeText(getApplicationContext(),"Price name can not be empty",Toast.LENGTH_SHORT).show();
                } else if(photoUri == null) {
                    Toast.makeText(getApplicationContext(),"Please select a photo",Toast.LENGTH_SHORT).show();
                } else if(quantityAvailabe.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter available quantity", Toast.LENGTH_SHORT).show();
                } else {
                    MainActivity.db.insert(productName, price, quantityAvailabe, photoUri.toString(), "0");
                    Toast.makeText(getApplicationContext(),"Product added",Toast.LENGTH_SHORT).show();
                    finish();
                    }
                }
            });
        }

    URI uri;

    @Override
    public void onDialogTakePhotoClick(DialogFragment dialog) {
        Log.i("onDialogTakePhotoClick", "called");
        dialog.dismiss();

        int permissionCheck = ContextCompat.checkSelfPermission(AddProductActivity.this, android.Manifest.permission.CAMERA);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // ask for permission
            ActivityCompat.requestPermissions(AddProductActivity.this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            Toast.makeText(AddProductActivity.this, "Camera permission already granted!", Toast.LENGTH_SHORT);
            takePhoto();
        }
    }

    @Override
    public void onDialogSelectFromGalleryClick(DialogFragment dialog) {
        Log.i("onDialog..GalleryClick", "called");
        dialog.dismiss();

        int permissionCheck = ContextCompat.checkSelfPermission(AddProductActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // ask for permission
            ActivityCompat.requestPermissions(AddProductActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        } else {
            Toast.makeText(AddProductActivity.this, "External storage read permission already granted!", Toast.LENGTH_SHORT);
            getPhotoFromGallery();
        }
    }

    public void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                // ...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "io.github.riyanshkarani011235.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    File getPhotoFromGalleryPhotoFile;
    boolean getPhotoFromGalleryFileCreateSuccessful = false;
    public void getPhotoFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Ensure that there's a camera activity to handle the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                getPhotoFromGalleryPhotoFile = photoFile;
            } catch (IOException ex) {
                // Error occurred while creating the File
                // ...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                getPhotoFromGalleryFileCreateSuccessful = true;
            }
        }

        Log.i("getPhotoFromGallery", Environment.getExternalStorageDirectory().toString());

        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PHOTO_FROM_GALLERY);
    }

    private File createImageFile() throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_.jpg";
        Log.i("files dir", getFilesDir().toString());
        File imagePath = new File(getFilesDir(), "images");
        // check if images directory exists, if not create a new one
        if(!imagePath.exists()) {
            imagePath.mkdir();
        }
        File image = new File(imagePath, imageFileName);
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, String permissions[], int[] grantResults) {
        switch(requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    Toast.makeText(AddProductActivity.this, "permission not granted, this feature can not be used", Toast.LENGTH_SHORT).show();
                }
                break;
            }case EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPhotoFromGallery();
                } else {
                    Toast.makeText(AddProductActivity.this, "permission not granted, this feature can not be used", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    String mCurrentPhotoPath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // we now have the uri
            photoUri = Uri.parse(mCurrentPhotoPath);
        } else if (requestCode == REQUEST_PHOTO_FROM_GALLERY && resultCode == RESULT_OK) {
            photoUri = data.getData();
            if(getPhotoFromGalleryFileCreateSuccessful) {
                try {
                    InputStream is = getContentResolver().openInputStream(photoUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    // TODO make sure that this bitmap does not take time to load
                    // TODO either by using an AsyncActivity, or using BitmapFactory.Options

                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(getPhotoFromGalleryPhotoFile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (out != null) {
                                out.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    photoUri = Uri.parse(mCurrentPhotoPath);
                } catch (java.io.FileNotFoundException e) {
                    // TODO handle file not found exception
                    return;
                }
            } else {
                // TODO could not get file from gallery
            }
        }
        if(photoUri != null) {
            Toast.makeText(AddProductActivity.this, photoUri.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
