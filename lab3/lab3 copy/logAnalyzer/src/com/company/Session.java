package com.company;

public class Session {
    private String sessionID;
    private String customerID;

    public Session(String sessionID, String customerID) {
        this.sessionID = sessionID;
        this.customerID = customerID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getCustomerID() {
        return customerID;
    }
}
