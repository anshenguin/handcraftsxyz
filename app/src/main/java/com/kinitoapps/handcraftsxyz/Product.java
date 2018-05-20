package com.kinitoapps.handcraftsxyz;

/**
 * Created by HP INDIA on 18-Apr-18.
 */

public class Product {
    private int id;
    private String productName;
    private double price;
    private String image;
    private String sellerName;
    private String productID;

    public Product(int id,String image,String productName,double price, String sellerName, String productID) {
        this.id = id;
        this.productName=productName;
        this.price = price;
        this.image = image;
        this.sellerName = sellerName;
        this.productID = productID;
    }

    public int getId() {
        return id;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
