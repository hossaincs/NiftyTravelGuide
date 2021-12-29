package com.example.niftytravelguide.ui.driver_list.helpline;

public class HelpModel {
    String helpNumber,helpOrganization;

    public HelpModel() {
    }

    public HelpModel(String helpNumber, String helpOrganization) {
        this.helpNumber = helpNumber;
        this.helpOrganization = helpOrganization;
    }

    public String getHelpNumber() {
        return helpNumber;
    }

    public void setHelpNumber(String helpNumber) {
        this.helpNumber = helpNumber;
    }

    public String getHelpOrganization() {
        return helpOrganization;
    }

    public void setHelpOrganization(String helpOrganization) {
        this.helpOrganization = helpOrganization;
    }
}
