package com.company;

public class Buy {
    private String productId;
    private double productPrice;
    private String productQuantity;

    public Buy(String productID, String productPrice, String productQuantity) {
        this.productId = productID;
        this.productPrice = Double.parseDouble(productPrice);
        this.productQuantity = productQuantity;
    }

    public String getProduct() {
        return productId;
    }

    public double getPriceCost() {
        return productPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }
}
