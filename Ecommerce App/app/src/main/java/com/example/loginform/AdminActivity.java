package com.example.loginform;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button deleteButton;

    EditText etName, etPrice;
    Button btnAdd;
    ListView lvProducts;

    DBConn db;
    ArrayList<Product> productList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        btnAdd = findViewById(R.id.btnAdd);
        lvProducts = findViewById(R.id.lvProducts);
        emailEditText = findViewById(R.id.emailEditText);
        deleteButton = findViewById(R.id.deleteButton);

        db = new DBConn(this); // ONE database object

        // Delete user
        deleteButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter an email to delete", Toast.LENGTH_SHORT).show();
                return;
            }

            db.deleteUser(email);
            Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show();
            emailEditText.setText("");
        });

        loadProducts();

        // Add product
        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String price = etPrice.getText().toString().trim();

            if (name.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean added = db.addProduct(name, "$" + price);

            if (added) {
                Toast.makeText(this, "Product added", Toast.LENGTH_SHORT).show();
                etName.setText("");
                etPrice.setText("");
                loadProducts();
            } else {
                Toast.makeText(this, "Error adding product", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProducts() {
        productList = db.getProducts();

        ArrayList<String> productNames = new ArrayList<>();
        for (Product p : productList) {
            productNames.add(p.getName() + " - " + p.getPrice());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productNames);
        lvProducts.setAdapter(adapter);

        // Delete product when clicked
        lvProducts.setOnItemClickListener((parent, view, position, id) -> {
            Product selected = productList.get(position);

            boolean deleted = db.deleteProduct(selected.getName());

            if (deleted) {
                Toast.makeText(this, "Product deleted", Toast.LENGTH_SHORT).show();
                loadProducts();
            } else {
                Toast.makeText(this, "Error deleting product", Toast.LENGTH_SHORT).show();
            }
        });
    }
}