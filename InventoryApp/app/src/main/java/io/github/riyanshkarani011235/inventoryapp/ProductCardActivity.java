package io.github.riyanshkarani011235.inventoryapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProductCardActivity extends AppCompatActivity {

    static void setOnClick_seeMoreButton(final Context context, View activityProductsCardView) {

        final TextView productName = (TextView) activityProductsCardView.findViewById(R.id.product_name);
        Button seeMoreButton = (Button) activityProductsCardView.findViewById(R.id.see_more);
        seeMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("click", "");
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("productName", productName.getText());
                context.startActivity(intent);
            }
        });
    }

    static void setOnClick_saleButton(final Context context, View activityProductsCardView) {
        final TextView productName = (TextView) activityProductsCardView.findViewById(R.id.product_name);
        Button saleButton = (Button) activityProductsCardView.findViewById(R.id.sale_button);
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = MainActivity.db.readProduct(productName.getText().toString());
                product.setProductQuantityAvailable(product.getProductQuantityAvailable() - 1);
                MainActivity.refreshListView();
            }
        });
    }
}

