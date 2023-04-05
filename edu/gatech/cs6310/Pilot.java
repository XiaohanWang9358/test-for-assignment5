package edu.gatech.cs6310;

public class Pilot {
    private String pilotAccount;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String taxID;
    private String licenseID;
    private Drone drone;
    private int successfulDelivery;



    public Pilot(String pilotAccount, String firstName, String lastName, String phoneNumber, String taxID, String licenseID, int successfulDelivery) {
        this.pilotAccount = pilotAccount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.taxID = taxID;
        this.licenseID = licenseID;
        this.successfulDelivery = successfulDelivery;
    }



    // Getters and setters for all fields
    public String getPilotAccount() {
        return pilotAccount;
    }

    public void setPilotAccount(String pilotAccount) {
        this.pilotAccount = pilotAccount;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTaxID() {
        return taxID;
    }

    public void setTaxID(String taxID) {
        this.taxID = taxID;
    }

    public String getLicenseID() {
        return licenseID;
    }

    public void setLicenseID(String licenseID) {
        this.licenseID = licenseID;
    }

    public int getSuccessfulDelivery() {
        return successfulDelivery;
    }



    public void setSuccessfulDelivery(int SuccessfulDelivery) {
        this.successfulDelivery = successfulDelivery;
    }

    public void display_pilots(){
        System.out.println("name:" + this.firstName + " " + this.lastName + ",phone:" + this.phoneNumber + ",taxID:" +
                this.taxID + ",licenseID:" + this.licenseID + ",experience:" +
                this.successfulDelivery);
    }

    public void setDrone(Drone drone){
        this.drone = drone;
    }

    public Drone getDrone(){
        return drone;
    }
    public void deliverOrder(){
        this.successfulDelivery += 1;
    }
}