package com.novartis.ecrs.ui.bean;

public class ScheduleComponentReportBean {
    private String meddraTerm;
    private String meddraExtension;
    private String safetyTopicOfInterest;
    private String crsName;
    private String riskPurposeList;
    private String socTerm;
    
    public ScheduleComponentReportBean() {
        super();
    }

    public void setMeddraTerm(String meddraTerm) {
        this.meddraTerm = meddraTerm;
    }

    public String getMeddraTerm() {
        return meddraTerm;
    }

    public void setMeddraExtension(String meddraExtension) {
        this.meddraExtension = meddraExtension;
    }

    public String getMeddraExtension() {
        return meddraExtension;
    }

    public void setSafetyTopicOfInterest(String safetyTopicOfInterest) {
        this.safetyTopicOfInterest = safetyTopicOfInterest;
    }

    public String getSafetyTopicOfInterest() {
        return safetyTopicOfInterest;
    }

    public void setCrsName(String crsName) {
        this.crsName = crsName;
    }

    public String getCrsName() {
        return crsName;
    }

    public void setRiskPurposeList(String riskPurposeList) {
        this.riskPurposeList = riskPurposeList;
    }

    public String getRiskPurposeList() {
        return riskPurposeList;
    }

    public void setSocTerm(String socTerm) {
        this.socTerm = socTerm;
    }

    public String getSocTerm() {
        return socTerm;
    }
}
