package com.company;

public class Buy {
    private String productId;
    private String productPrice;
    private String productQuantity;

    public Buy(String productID, String productPrice, String productQuantity) {
        this.productId = productID;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    public String getProductID() {
        return productId;
    }

    public Double getProductPrice() {
        return Double.parseDouble(productPrice);
    }

    public String getProductQuantity() {
        return productQuantity;
    }
}
