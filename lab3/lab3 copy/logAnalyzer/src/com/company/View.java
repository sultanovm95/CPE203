package com.company;

public class View {

    private String productID;
    private double viewPrice;



    public View(String productID, String viewPrice) {

        this.productID = productID;
        this.viewPrice = Double.parseDouble(viewPrice);
    }

    public String getProduct() {
        return productID;
    }

    public double getPriceCost() {
        return viewPrice;
    }




}
