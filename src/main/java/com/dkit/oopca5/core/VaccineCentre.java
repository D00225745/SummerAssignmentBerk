package com.dkit.oopca5.core;

public class VaccineCentre
{
    private String vaccineCentreID;
    private int vaccineCentreLevel;
    private String vaccineCentreTitle;
    private String institution;

    public VaccineCentre(String vaccineCentreID, int vaccineCentreLevel, String vaccineCentreTitle, String institution) {
        this.vaccineCentreID = vaccineCentreID;
        this.vaccineCentreLevel = vaccineCentreLevel;
        this.vaccineCentreTitle = vaccineCentreTitle;
        this.institution = institution;
    }

    public String getVaccineCentreID() {
        return vaccineCentreID;
    }

    public int getVaccineCentreLevel() {
        return vaccineCentreLevel;
    }

    public String getVaccineCentreTitle() {
        return vaccineCentreTitle;
    }

    public String getInstitution() {
        return institution;
    }

    @Override
    public String toString() {
        return "VaccineCentre{" +
                "vaccineCentreID='" + vaccineCentreID + '\'' +
                ", vaccineCentreLevel=" + vaccineCentreLevel +
                ", vaccineCentreTitle='" + vaccineCentreTitle + '\'' +
                ", institution='" + institution + '\'' +
                '}';
    }
}
