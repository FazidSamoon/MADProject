package com.example.madproject;

import java.util.Date;

public class WaterGoal {

    String waterGoal;
    String dailyMode;
    String completed;
    Date date;

    public WaterGoal() {
    }

    public String getWaterGoal() {
        return waterGoal;
    }

    public void setWaterGoal(String waterGoal) {
        this.waterGoal = waterGoal;
    }

    public String getDailyMode() {
        return dailyMode;
    }

    public void setDailyMode(String dailyMode) {
        this.dailyMode = dailyMode;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
