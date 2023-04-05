package edu.gatech.cs6310;

import java.util.HashMap;
import java.util.Map;

public class Order {
    public String orderID;
    private Customer customer;
    private Drone drone;
    private Map<String, OrderLine> orderLines;
    private Store store;
    public int cost;
    public int weight;

    public Order(Store store, String orderID, Drone drone, Customer customer) {
        this.orderID = orderID;
        this.customer = customer;
        this.drone = drone;
        this.orderLines = new HashMap<>();
        this.store = store;
        this.cost = 0;
        this.weight = 0;
    }

    public String getOrderID() {
        return orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Drone getDrone() {
        return drone;
    }

    public Map<String, OrderLine> getOrderLines() {
        return orderLines;
    }

    public class OrderLine {
        private String itemName;
        private int quantity;
        private int unitPrice;

        public OrderLine(String itemName, int quantity, int unitPrice) {
            this.itemName = itemName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        public String getItemName() {
            return itemName;
        }

        public int getQuantity() {
            return quantity;
        }

        public int getUnitPrice() {
            return unitPrice;
        }

        public int getTotalPrice() {
            return quantity * unitPrice;
        }

        public int getTotalWeight() {
            return store.getCatalog().get(itemName) * quantity;
        }


    }

    public void request_item(String itemName, int quantity, int unitPrice) {
        int totalWeight = store.getCatalog().get(itemName) * quantity;
        int totalPrice = unitPrice * quantity;

        if (this.customer.getPendingCredit() < totalPrice) {
            System.out.println("ERROR:customer_cant_afford_new_item");
        } else {
            if (this.drone.getRemainingCapacity() < totalWeight) {
                System.out.println("ERROR:drone_cant_carry_new_item");
            } else {
                if (orderLines.containsKey(itemName)) {
                    System.out.println("ERROR:item_already_ordered");

                } else {
                    OrderLine orderLine = new OrderLine(itemName, quantity, unitPrice);
                    orderLines.put(itemName, orderLine);
                    this.cost += totalPrice;
                    this.weight += totalWeight;
                    customer.setPendingCredit(totalPrice);
                    drone.setRemainingCapacity(totalWeight);
                    System.out.println("OK:change_completed");
                }
            }
        }
    }

    public void transfer_order(Drone drone){
        this.drone = drone;
    }



}