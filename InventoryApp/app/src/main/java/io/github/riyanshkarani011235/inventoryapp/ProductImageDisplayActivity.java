package io.github.riyanshkarani011235.inventoryapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ProductImageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_image_display);
        String productImageUri = getIntent().getStringExtra("productImageUri");

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(productImageUri.replace("file:", ""), bmOptions);

        ImageView productImage = (ImageView) findViewById(R.id.product_image);
        productImage.setImageBitmap(bitmap);
    }
}
