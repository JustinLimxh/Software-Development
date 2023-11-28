package com.allcodingtutorials.softwaredev;
public class FeedbackModel {
    private String feedbackText;

    public FeedbackModel() {
        // Default constructor required for Firebase
    }

    public FeedbackModel(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public String getFeedbackText() {
        return feedbackText;
    }
}
