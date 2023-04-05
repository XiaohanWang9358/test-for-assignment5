package edu.gatech.cs6310;

import java.util.HashMap;
import java.util.Map;

public class Customer {
    private String customerID;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private int rating;
    private int credit;
    private int pendingCredit;
    private Map<String, Order> orders;






    public Customer(String customerID, String firstName, String lastName, String phoneNumber, int rating, int credit){
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.credit = credit;
        this.address = address;
        this.pendingCredit = credit;
        this.orders = new HashMap<>();
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getCredit() {
        return credit;
    }












    public void start_order(Order newOrder) {
        String orderID = newOrder.getOrderID();

        orders.put(orderID, newOrder);
    }


    public int setPendingCredit(int totalPrice) {
        pendingCredit -= totalPrice;
        return pendingCredit;
    }

    public void completeOrder(String orderID, int price){
        this.credit -= price;
        this.orders.remove(orderID);
    }

    public void cancelOrder(String orderID){
        this.orders.remove(orderID);
    }


    public int getPendingCredit() {
        return pendingCredit;
    }
}
