package io.github.riyanshkarani011235.inventoryapp;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductActivity extends AppCompatActivity implements DeleteProductDialogFragment.DialogListener, OrderDialogFragment.DialogListener{

    ImageView productImageView;
    TextView productNameTextView;
    TextView productQuantityAvailableTextView;
    TextView productsOrdered;
    TextView productPriceTextView;

    Product product;

    @Override
    public void onYesClick(DialogFragment dialog) {
        MainActivity.db.deleteProduct(product.getProductName());
        finish();
    }

    @Override
    public void onNoClick(DialogFragment dialog) {

    }

    @Override
    public void onOkClick(DialogFragment dialog, int orderQuantity) {
        if(orderQuantity <= 0) {
            Toast.makeText(getApplicationContext(), "orderQuantity can not be 0", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        } else {
            String subjectLine = "Order for " + orderQuantity + " more " + product.getProductName() + "'s";
            product.setProductQuantityOrdered(product.getProductQuantityOrdered() + orderQuantity);
            refreshLayout();

            // email intent
            Log.i("sending email intent", "cheers");
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, "amazonproducts@amazon.co.uk");
            intent.putExtra(Intent.EXTRA_SUBJECT, subjectLine);
            startActivity(Intent.createChooser(intent, "Send Email"));
        }
    }

    @Override
    public void onCancelClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Bundle extras = getIntent().getExtras();
        final String productName = extras.getString("productName");

        product = MainActivity.db.readProduct(productName);

        productImageView = (ImageView) findViewById(R.id.product_image);
        productNameTextView = (TextView) findViewById(R.id.product_name);
        productQuantityAvailableTextView = (TextView) findViewById(R.id.product_quantity_available);
        productsOrdered = (TextView) findViewById(R.id.products_ordered);
        productPriceTextView = (TextView) findViewById(R.id.product_price);
        Button receiveShipmentButton = (Button) findViewById(R.id.receive_shipment_button);
        Button trackSaleButton = (Button) findViewById(R.id.track_sale_button);
        Button orderButton = (Button) findViewById(R.id.order_button);
        Button deleteProductButton = (Button) findViewById(R.id.delete_product_button);

        receiveShipmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(product.getProductQuantityOrdered() != 0) {
                    Log.i("productQuantityOrdered", "" + product.getProductQuantityAvailable());
                    product.setProductQuantityAvailable(product.getProductQuantityAvailable() + product.getProductQuantityOrdered());
                    product.setProductQuantityOrdered(0);
                    refreshLayout();
                } else {
                    Log.i("makingToast", "cheers");
                    Toast.makeText(getApplicationContext(), "Order quantity is 0, what are you receiving?", Toast.LENGTH_SHORT).show();
                }
            }
        });

        trackSaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(product.getProductQuantityAvailable() == 0) {
                    Toast.makeText(getApplicationContext(), "0 Items available, what are you selling", Toast.LENGTH_SHORT).show();
                }
                product.setProductQuantityAvailable(product.getProductQuantityAvailable() - 1);
                refreshLayout();
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDialogFragment newFragment = new OrderDialogFragment();
                newFragment.show(getFragmentManager(), "orderDialogFragment");
            }
        });

        deleteProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteProductDialogFragment newFragment = new DeleteProductDialogFragment();
                newFragment.show(getFragmentManager(), "deletePhotoDialogFragment");
            }
        });

        productImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ProductImageDisplayActivity.class);
                intent.putExtra("productImageUri", product.getProductImageUri());
                startActivity(intent);
            }
        });

        refreshLayout();
    }

    public void refreshLayout() {
        productNameTextView.setText(product.getProductName());
        productPriceTextView.setText("" + product.getProductPrice());
        productQuantityAvailableTextView.setText("" + product.getProductQuantityAvailable());
        productsOrdered.setText("" + product.getProductQuantityOrdered());

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 8;
        Bitmap bitmap = BitmapFactory.decodeFile(product.getProductImageUri().replace("file:", ""), bmOptions);
        productImageView.setImageBitmap(bitmap);
    }
}
