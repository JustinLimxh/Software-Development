package com.allcodingtutorials.softwaredev;

public class CartItem {
    private DataClass dataClass;
    private int quantity;

    public CartItem() {
        // Default constructor required for Firebase
    }

    public CartItem(DataClass dataClass, int quantity) {
        this.dataClass = dataClass;
        this.quantity = quantity;
    }

    public DataClass getDataClass() {
        return dataClass;
    }

    public int getQuantity() {
        return quantity;
    }
}
