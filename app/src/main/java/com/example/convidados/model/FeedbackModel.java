package com.example.convidados.model;

public class FeedbackModel {

    public FeedbackModel(String message) {
        this.mMessage = message;
    }

    public FeedbackModel(String message, boolean success) {
        this.mMessage = message;
        this.mSuccess = success;
    }

    public boolean isSuccess() {
        return this.mSuccess;
    }

    public String getMessage() {
        return  this.mMessage;
    }

    private boolean mSuccess = true;
    private String mMessage = "";

}
