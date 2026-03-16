package com.example.loginform;

public class Product {
        private String name;
        private String price;
        private int image; // drawable resource id

        private String category;

        public Product(String name, String price, int image, String category) {
            this.name = name;
            this.price = price;
            this.image = image;
            this.category = category;
        }

        // Getters
        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

        public int getImage() {
            return image;
        }

        public String getCategory(){
            return  category;
        }

        // Setters (optional)
        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setImage(int image) {
            this.image = image;
        }
}

