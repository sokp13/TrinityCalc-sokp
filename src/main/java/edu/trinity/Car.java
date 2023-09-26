package edu.trinity;

import java.time.Year;

public class Car {

    private String make;
    private String model;
    private Year year;
    private boolean engine;
    private int mileage = 0;

    public Car(String m, String o, Year y) {
        make = m;
        model = o;
        year = y;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public Year getYear() {
        return year;
    }

    public boolean isRunning() {
        return engine;
    }

    public void start() {
        engine = true;
    }

    public void stop() {
        engine = false;
    }

    public void drive(int distance) {
        if (engine){
            mileage += distance;
        }
    }

    public int getMiles() {
        return mileage;
    }
}
