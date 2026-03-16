package com.example.loginform;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UserAdapter adapter;
    ArrayList<Product> productList;
    DBConn db; // database connection
    Button btnCart;



    Button btnElectronics;
    Button btnFashion;
    Button btnSports;
    Button btnGadgets;
    Button btnAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_item); // make sure this is your main layout

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize database
        db = new DBConn(this);

        // Load products from database
        productList = db.getProducts();

        // Set up adapter
        adapter = new UserAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        btnCart = findViewById(R.id.btnCart);

        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        btnElectronics = findViewById(R.id.btnElectronics);
        btnFashion = findViewById(R.id.btnFashion);
        btnSports = findViewById(R.id.btnSports);
        btnGadgets = findViewById(R.id.btnGadgets);
        btnAll = findViewById(R.id.btnAll);


        btnElectronics.setOnClickListener(v -> filterProducts("Electronics"));
        btnFashion.setOnClickListener(v -> filterProducts("Fashion"));
        btnSports.setOnClickListener(v -> filterProducts("Sports"));
        btnGadgets.setOnClickListener(v -> filterProducts("Gadgets"));
        btnAll.setOnClickListener(v -> {
            adapter.updateList(productList);
        });

    }

    private void filterProducts(String category)
    {
        ArrayList<Product> filtered = new ArrayList<>();

        for(Product p : productList)
        {
            if(p.getCategory().equals(category))
            {
                filtered.add(p);
            }
        }
        adapter.updateList(filtered);
    }

}