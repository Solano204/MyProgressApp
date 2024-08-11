package com.example.myprogress.app.Exceptions;

import java.time.LocalDate;

public class Error {
    private String message;
    private String error;
    private int numberStatus;
    private LocalDate date;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public int getNumberStatus() {
        return numberStatus;
    }
    public void setNumberStatus(int numberStatus) {
        this.numberStatus = numberStatus;
    }
   public LocalDate getDate() {
       return date;
   }public void setDate(LocalDate date) {
       this.date = date;
   }
}