package com.example.loginform;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

        private Context context;
        private ArrayList<Product> productList;

        public UserAdapter(Context context, ArrayList<Product> productList) {
            this.context = context;
            this.productList = productList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Product product = productList.get(position);

            holder.productName.setText(product.getName());
            holder.productPrice.setText(product.getPrice());
            holder.productImage.setImageResource(product.getImage()); // placeholder image

            holder.addButton.setOnClickListener(v -> {
                // Example action when Add button is clicked
                CartManager.getInstance().addToCart(product);
                Toast.makeText(context, product.getName() + " added to cart!", Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        // ViewHolder class
        public static class ViewHolder extends RecyclerView.ViewHolder {

            ImageView productImage;
            TextView productName, productPrice;
            Button addButton;

            public ViewHolder(View itemView) {
                super(itemView);

                productImage = itemView.findViewById(R.id.productImage);
                productName = itemView.findViewById(R.id.productName);
                productPrice = itemView.findViewById(R.id.productPrice);
                addButton = itemView.findViewById(R.id.addButton);
            }
        }

        public void updateList(ArrayList<Product> newList)
        {
            productList = newList;
            notifyDataSetChanged();
        }
    }

