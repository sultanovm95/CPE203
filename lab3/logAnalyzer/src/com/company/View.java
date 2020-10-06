package com.company;

public class View {
    private String sessionID;
    private String productID;
    private String viewPrice;



    public View(String sessionID, String productID, String viewPrice) {
        this.sessionID = sessionID;
        this.productID = productID;
        this.viewPrice = viewPrice;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getProduct() {
        return productID;
    }

    public Double getViewPrice() {
        return Double.parseDouble(viewPrice);
    }




}
