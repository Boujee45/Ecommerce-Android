package com.example.loginform;

import java.util.ArrayList;

public class CartManager {

    private static CartManager instance;
    private ArrayList<CartItem> cartList;

    private CartManager() {
        cartList = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    // Add product to cart
    public void addToCart(Product product) {

        for (CartItem item : cartList) {
            if (item.getName().equals(product.getName())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }

        cartList.add(new CartItem(product.getName(), product.getPrice(), 1));
    }

    // Get cart list
    public ArrayList<CartItem> getCart() {
        return cartList;
    }

    // Remove item
    public void removeItem(int position) {
        cartList.remove(position);
    }

    public void clearCart()
    {
        cartList.clear();
    }
}