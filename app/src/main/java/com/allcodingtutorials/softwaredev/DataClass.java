package com.allcodingtutorials.softwaredev;

public class DataClass {
    private String imageURL, caption;
    private double price;  // Add the price field

    // Default constructor required by Firebase
    public DataClass() {
        // Default constructor must be public
    }

    public DataClass(String imageUrl, String caption, double price) {
        this.imageURL = imageUrl;
        this.caption = caption;
        this.price = price;
    }

    // Getter and setter methods for the price field
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
