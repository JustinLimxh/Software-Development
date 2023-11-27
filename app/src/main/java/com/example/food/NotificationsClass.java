package com.example.food;

public class NotificationsClass {
    private String orderId;
    private String orderDescription;

    // Empty constructor for Firebase
    public NotificationsClass() {
    }

    public NotificationsClass(String orderId, String orderDescription) {
        this.orderId = orderId;
        this.orderDescription = orderDescription;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderDescription() {
        return orderDescription;
    }
}
