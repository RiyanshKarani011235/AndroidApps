package io.github.riyanshkarani011235.inventoryapp;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ironstein on 27/07/16.
 */
public class ProductsListAdapter extends ArrayAdapter<Product> {

    public ProductsListAdapter(Activity context, ArrayList<Product> products) {
        super(context, 0, products);
    }

    static class ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView quantity;
        TextView price;
    }

    ViewHolder viewHolder;

    public View getView(int position, View convertView, ViewGroup parent) {
        View productView = convertView;
        if(productView == null) {
            productView = LayoutInflater.from(getContext()).inflate(R.layout.activity_product_card, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.productImage = (ImageView) productView.findViewById(R.id.product_image);
            viewHolder.productName = (TextView) productView.findViewById(R.id.product_name);
            viewHolder.quantity = (TextView) productView.findViewById(R.id.quantity);
            viewHolder.price = (TextView) productView.findViewById(R.id.price);
            productView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) productView.getTag();
        }

        ProductCardActivity.setOnClick_seeMoreButton(getContext(), productView);
        ProductCardActivity.setOnClick_saleButton(getContext(), productView);
        viewHolder.productName.setText(getItem(position).productName);
        viewHolder.price.setText(getItem(position).productPrice);
        viewHolder.quantity.setText(getItem(position).productQuantityAvailable);

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 32;
        Bitmap bitmap = BitmapFactory.decodeFile((getItem(position).productImageUri).replace("file:", ""), bmOptions);
        viewHolder.productImage.setImageBitmap(bitmap);
        return productView;
    }


}
