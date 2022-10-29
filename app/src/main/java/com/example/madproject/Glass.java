package com.example.madproject;

import java.util.Date;

public class Glass {

    String amount;
    Date date;

    public Glass() {
    }

    public Glass(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
