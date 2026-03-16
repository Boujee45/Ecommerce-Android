package com.example.loginform;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ListView cartListView1;
    CartAdapter adapter;
    ArrayList<CartItem> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView1 = findViewById(R.id.cartListView);

        cartList = CartManager.getInstance().getCart();

        adapter = new CartAdapter(this, cartList);
        cartListView1.setAdapter(adapter);
    }
}