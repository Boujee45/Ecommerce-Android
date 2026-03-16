package com.example.loginform;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBConn extends SQLiteOpenHelper {

    private static final String dbName = "EcommerceDB";
    private static final int dbVersion = 3; // IMPORTANT: increased version

    private static final String USERS_TABLE = "users";
    private static final String PRODUCTS_TABLE = "products";

    private static final String id = "id";
    private static final String name = "name";
    private static final String email = "email";
    private static final String phoneNo = "phoneNo";
    private static final String username = "username";
    private static final String password = "password";

    public DBConn(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Users table
        db.execSQL("CREATE TABLE " + USERS_TABLE + "(" +
                id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                name + " TEXT, " +
                email + " TEXT UNIQUE, " +
                phoneNo + " TEXT, " +
                username + " TEXT, " +
                password + " TEXT)");

        // Products table
        db.execSQL("CREATE TABLE " + PRODUCTS_TABLE + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "price TEXT)");

        // Sample products
        db.execSQL("INSERT INTO products(name,price) VALUES('Headphones','$129.99')");
        db.execSQL("INSERT INTO products(name,price) VALUES('Smart Watch','$279.99')");
        db.execSQL("INSERT INTO products(name,price) VALUES('Sunglasses','$159.99')");
        db.execSQL("INSERT INTO products(name,price) VALUES('Backpack','$179.99')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS products");

        onCreate(db);
    }

    // Add user
    public void addUser(Users user) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();

            values.put(name, user.getName());
            values.put(email, user.getEmail());
            values.put(phoneNo, user.getPhoneNo());
            values.put(username, user.getUsername());
            values.put(password, user.getPassword());

            db.insert(USERS_TABLE, null, values);
        } catch (Exception e) {
            Log.e("DBConn", "Error adding user", e);
        }
    }

    // Check login
    public boolean checkUser(String usernameInput, String passwordInput) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE username=? AND password=?",
                new String[]{usernameInput, passwordInput}
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();

        return exists;
    }

    // Delete user
    public void deleteUser(String emailInput) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("users", "email=?", new String[]{emailInput});
    }

    // Get products
    public ArrayList<Product> getProducts() {

        ArrayList<Product> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM products", null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String price = cursor.getString(cursor.getColumnIndexOrThrow("price"));

                int image = R.drawable.mountain_side;
                String category = "";

                if (name.equals("Headphones")) {
                    image = R.drawable.headphones;
                    category = "Electronics";
                } else if (name.equals("Smart Watch")) {
                    image = R.drawable.smartwatch;
                    category = "Gadgets";
                } else if (name.equals("Sunglasses")) {
                    image = R.drawable.sunglasses;
                    category = "Fashion";
                } else if (name.equals("Backpack")) {
                    image = R.drawable.backpack;
                    category = "Fashion";
                }

                list.add(new Product(name, price, image,category));

            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    // Add product
    public boolean addProduct(String name, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("price", price);

        long result = db.insert("products", null, values);
        return result != -1;
    }

    // Delete product
    public boolean deleteProduct(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deleted = db.delete("products", "name=?", new String[]{name});
        return deleted > 0;
    }

    // Update product
    public boolean updateProduct(String oldName, String newName, String newPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", newName);
        values.put("price", newPrice);

        int updated = db.update("products", values, "name=?", new String[]{oldName});
        return updated > 0;
    }
}