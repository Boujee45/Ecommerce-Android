package com.example.loginform;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {

    Context context;
    ArrayList<CartItem> cartList;

    public CartAdapter(Context context, ArrayList<CartItem> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        }

        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtQuantity = view.findViewById(R.id.txtQuantity);
        Button btnPlus = view.findViewById(R.id.btnPlus);
        Button btnMinus = view.findViewById(R.id.btnMinus);
        Button btnRemove = view.findViewById(R.id.btnRemove);

        CartItem item = cartList.get(position);

        txtName.setText(item.getName() + " - " + item.getPrice());
        txtQuantity.setText(String.valueOf(item.getQuantity()));

        btnPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyDataSetChanged();
        });

        btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                notifyDataSetChanged();
            }
        });

        btnRemove.setOnClickListener(v -> {
            cartList.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}