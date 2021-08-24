package com.dkit.oopca5.core;

public class VaccineCentre {
    private int centerId;
    private String location;

    public VaccineCentre(int centerId, String location) {
        this.centerId = centerId;
        this.location = location;

    }

    public int getCentreId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Vaccine Center{" + "Center ID=" + centerId + ", locatiot='" + location + '\'' + '}';
    }

}
