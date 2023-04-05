package edu.gatech.cs6310;

import java.util.Map;
import java.util.TreeMap;

public class Drone {
    private String droneID;
    private int capacity;
    private int fuel;
    private int num_orders;
    private int remaining_cap;
    private Store store;
    private Pilot pilot;
    public Map<String, Order> orders = new TreeMap<>();

    public Drone(Store store, String droneID, int capacity, int fuel){
        this.droneID = droneID;
        this.store = store;
        this.capacity = capacity;
        this.fuel = fuel;
        this.num_orders = 0;
        this.remaining_cap = capacity;

    }

    public Store getStore() {
        return store;
    }

    public String getDroneID(){
        return droneID;
    }

    public int getCapacity(){
        return capacity;
    }


    public int getFuel(){
        return fuel; // remaining  number of deliveries that the drone can make before needing refueling
    }

    public int setNumOrders(){
        num_orders += 1;
        return num_orders;
    }

    public int getNum_orders(){
        return num_orders;
    }





    public int setRemainingCapacity(int weight){
        remaining_cap -= weight;
        return remaining_cap;
    }

    public int getRemainingCapacity(){
        return remaining_cap;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public void deliverOrder(String orderID, int weight){
        this.remaining_cap += weight;
        this.fuel -= 1;
        this.num_orders -= 1;
        this.orders.remove(orderID);
    }

    public void cancelOrder(String orderID, int weight){

        this.orders.remove(orderID);
        this.num_orders -= 1;
        this.remaining_cap += weight;
    }



    public void transfer_order(Drone newDrone, String orderID, int weight){
        this.num_orders -= 1;
        this.orders.remove(orderID);
        newDrone.num_orders += 1;
        orders.remove(orderID);
        this.remaining_cap += weight;
        newDrone.remaining_cap -= weight;
    }








}
