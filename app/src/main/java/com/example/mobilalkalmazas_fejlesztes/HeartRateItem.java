package com.example.mobilalkalmazas_fejlesztes;

public class HeartRateItem {
    private String date;
    private int pulse;
    private String bloodPressure;
    private int oxygen;

    public HeartRateItem(String date, int pulse, String bloodPressure, int oxygen) {
        this.date = date;
        this.pulse = pulse;
        this.bloodPressure = bloodPressure;
        this.oxygen = oxygen;
    }

    public String getDate() {
        return date;
    }

    public int getPulse() {
        return pulse;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public int getOxygen() {
        return oxygen;
    }
}
