package edu.gatech.cs6310;

import java.util.*;

public class DeliveryService {


    public void commandLoop() {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        HashMap<String, Store> stores = new HashMap<>();
        HashMap<String, Pilot> pilots = new HashMap<>();
        Map<String, Customer> customers = new HashMap<>();
        HashMap<String, Store> drones = new HashMap<>();
        Map<String, Order> orders; // order ID -> order
        int successfulTransfers = 0;






        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);
                if (wholeInputLine.startsWith("//")) continue;

                if (tokens[0].equals("make_store")) {
                    //System.out.println("store: " + tokens[1] + ", revenue: " + tokens[2]);

                    String storeName = tokens[1];
                    int revenue = Integer.parseInt(tokens[2].trim());
                    if (stores.containsKey(storeName)) {
                        System.out.println("ERROR:store_identifier_already_exists");
                    }else{
                        Store store = new Store(storeName, revenue);
                        stores.put(storeName, store);
                        System.out.println("OK:change_completed");
                    }



                } else if (tokens[0].equals("display_stores")) {
                    //System.out.println("no parameters needed");
                    if (stores.isEmpty()) {
                        System.out.println("No stores found");
                    }
                    List<Store> sortedStores = new ArrayList<>(stores.values());
                    Collections.sort(sortedStores, Comparator.comparing(Store::getName)); // Sort by name
                    StringBuilder sb = new StringBuilder();
                    for (Store store : sortedStores) {
                        sb.append("name:").append(store.getName()).append(",revenue:").append(store.getRevenue()).append("\n");
                    }
                    System.out.println(sb.toString().trim());
                    System.out.println("OK:display_completed");


                } else if (tokens[0].equals("sell_item")) {
                    //System.out.println("store: " + tokens[1] + ", item: " + tokens[2] + ", weight: " + tokens[3]);

                    String storeName = tokens[1];
                    String itemName = tokens[2];
                    int itemWeight = Integer.parseInt(tokens[3].trim());
                    if (!stores.containsKey(storeName)) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }else{
                    Store store = stores.get(storeName);
                    String result = store.sell_item(storeName, itemName, itemWeight);
                    System.out.println(result);}



                } else if (tokens[0].equals("display_items")) {
                    //System.out.println("store: " + tokens[1]);
                    String storeName = tokens[1];
                    if (!stores.containsKey(storeName)) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }else {
                        Store store = stores.get(storeName);
                        store.display_items();
                        System.out.println("OK:display_completed");
                    }


                } else if (tokens[0].equals("make_pilot")) {
                    //System.out.print("account: " + tokens[1] + ", first_name: " + tokens[2] + ", last_name: " + tokens[3]);
                    //System.out.println(", phone: " + tokens[4] + ", tax: " + tokens[5] + ", license: " + tokens[6] + ", experience: " + tokens[7]);
                    String pilotAccount = tokens[1];
                    String firstName = tokens[2];
                    String lastName = tokens[3];
                    String phoneNumber = tokens[4];
                    String taxID = tokens[5];
                    String licenseID = tokens[6];
                    int successfulDelivery = Integer.parseInt(tokens[7].trim());

                    if (pilots.containsKey(pilotAccount)) {
                        System.out.println("ERROR:pilot_identifier_already_exists");
                    } else {
                        // Check if the license ID already exists for another pilot
                        boolean licenseExists = false;
                        for (Pilot pilot : pilots.values()) {
                            if (pilot.getLicenseID().equals(licenseID)) {
                                licenseExists = true;
                                break;
                            }
                        }
                        if (licenseExists) {
                            System.out.println("ERROR:pilot_license_already_exists");
                        } else {
                            // Create a new pilot and add it to the map
                            Pilot newPilot = new Pilot(pilotAccount, firstName, lastName, phoneNumber, taxID, licenseID, successfulDelivery);
                            pilots.put(pilotAccount, newPilot);
                            System.out.println("OK:change_completed");
                        }
                    }
                } else if (tokens[0].equals("display_pilots")) {

                    // Get a list of all the pilots' identifiers
                    List<String> identifiers = new ArrayList<>(pilots.keySet());
                    // Sort the identifiers in ascending order
                    Collections.sort(identifiers);
                    // Loop through the sorted identifiers and display the information of each pilot
                    for (String identifier : identifiers) {
                        Pilot pilot = pilots.get(identifier);
                        System.out.println("name:" + pilot.getFirstName() + "_" + pilot.getLastName() + ",phone:" + pilot.getPhoneNumber() + ",taxID:" +
                                pilot.getTaxID() + ",licenseID:" + pilot.getLicenseID() + ",experience:" +
                                pilot.getSuccessfulDelivery());
                    }
                    System.out.println("OK:display_completed");


                } else if (tokens[0].equals("make_drone")) {
                    //System.out.println("store: " + tokens[1] + ", drone: " + tokens[2] + ", capacity: " + tokens[3] + ", fuel: " + tokens[4]);

                    String storeName = tokens[1];
                    String droneID = tokens[2];
                    int capacity = Integer.parseInt(tokens[3]);
                    int fuel = Integer.parseInt(tokens[4]);
                    // Check if the store exists
                    if (!stores.containsKey(storeName)) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }else {
                        Store store = stores.get(storeName);
                        String result = store.make_drone(store, droneID, capacity, fuel);
                        System.out.println(result);
                    }


                } else if (tokens[0].equals("display_drones")) {
                    //System.out.println("store: " + tokens[1]);
                    if (!stores.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }else {
                        Store store = stores.get(tokens[1]);
                        String result = store.display_drones();
                        System.out.println(result);
                    }


                } else if (tokens[0].equals("fly_drone")) {
                    //System.out.println("store: " + tokens[1] + ", drone: " + tokens[2] + ", pilot: " + tokens[3]);
                    String storeName = tokens[1];
                    String droneID = tokens[2];
                    String pilotAccount = tokens[3];
                    // Check if the store and drone exist
                    if (!stores.containsKey(storeName)) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = stores.get(storeName);
                        if (!store.getDrones().containsKey(droneID)) {
                            System.out.println("ERROR:drone_identifier_does_not_exist");
                        } else {
                            Drone drone = store.getDrones().get(droneID);
                            // Check if the pilot exists
                            if (!pilots.containsKey(pilotAccount)) {
                                System.out.println("ERROR:pilot_identifier_does_not_exist");
                            } else {
                                Pilot pilot = pilots.get(pilotAccount);
                                 //Check if the drone is already being flown by another pilot
                                if (drone.getPilot() != null && !drone.getPilot().equals(pilot)) {
                                    // Replace the pilot of the drone
                                    Pilot oldPilot = drone.getPilot();
                                    oldPilot.setDrone(null);
                                    drone.setPilot(pilot);
                                    pilot.setDrone(drone);
                                    System.out.println("OK:change_completed");
                                } else {
                                    // Assign the pilot to the drone
                                    if (pilot.getDrone() != null && !pilot.getDrone().equals(drone)) {
                                        // If the pilot is already flying another drone, remove the pilot from that drone
                                        Drone oldDrone = pilot.getDrone();
                                        oldDrone.setPilot(null);
                                    }
                                    drone.setPilot(pilot);
                                    pilot.setDrone(drone);
                                    System.out.println("OK:change_completed");
                                }
                            }
                        }
                    }
                } else if (tokens[0].equals("make_customer")) {
                    //System.out.print("account: " + tokens[1] + ", first_name: " + tokens[2] + ", last_name: " + tokens[3]);
                    //System.out.println(", phone: " + tokens[4] + ", rating: " + tokens[5] + ", credit: " + tokens[6]);
                    String customerID = tokens[1];
                    String firstName = tokens[2];
                    String lastName = tokens[3];
                    String phoneNumber = tokens[4];
                    int rating = Integer.parseInt(tokens[5]);
                    int credit = Integer.parseInt(tokens[6]);

                    if (customers.containsKey(customerID)) {
                        System.out.println("ERROR:customer_identifier_already_exists");
                    } else {
                        Customer customer = new Customer(customerID, firstName, lastName, phoneNumber, rating, credit);
                        customers.put(customerID, customer);
                        System.out.println("OK:change_completed");
                    }

                } else if (tokens[0].equals("display_customers")) {
                    //System.out.println("no parameters needed");
                    // Get a list of all the customer identifiers
                    List<String> identifiers = new ArrayList<>(customers.keySet());
                    // Sort the identifiers in ascending order
                    Collections.sort(identifiers);
                    // Loop through the sorted identifiers and display the information of each customer
                    for (String identifier : identifiers) {
                        Customer customer = customers.get(identifier);
                        System.out.println("name:" + customer.getFirstName() + "_" + customer.getLastName() +
                                ",phone:" + customer.getPhoneNumber() + ",rating:" + customer.getRating() +
                                ",credit:" + customer.getCredit());
                    }
                    System.out.println("OK:display_completed");

                } else if (tokens[0].equals("start_order")) {
                    //System.out.println("store: " + tokens[1] + ", order: " + tokens[2] + ", drone: " + tokens[3] + ", customer: " + tokens[4]);
                    String storeName = tokens[1];
                    String orderID = tokens[2];
                    String droneID = tokens[3];
                    String customerID = tokens[4];
                    // check if store name exists when start the order
                    if (!stores.containsKey(storeName)) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }else {
                        if (!customers.containsKey(customerID)) {
                            System.out.println("ERROR:customer_identifier_does_not_exist");
                        } else {
                            Store store = stores.get(storeName);

                            if (store.getOrders().containsKey(orderID)) {
                                System.out.println("ERROR:order_identifier_already_exists");
                            } else {
                                if (!store.getDrones().containsKey(droneID)) {
                                    System.out.println("ERROR:drone_identifier_does_not_exist");
                                } else {
                                    // create new order with orderId and customer
                                    Customer customer  = customers.get(customerID);
                                    Drone drone = store.getDrones().get(droneID);
                                    Order newOrder = new Order(store, orderID, drone, customer);
                                    store.add_order(newOrder);
                                    customer.start_order(newOrder);
                                    drone.setNumOrders();
                                    System.out.println("OK:change_completed");
                                }
                            }
                        }
                    }

                } else if (tokens[0].equals("display_orders")) {
                    //System.out.println("store: " + tokens[1]);
                    String storeName = tokens[1];
                    if (!stores.containsKey(storeName)) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }else{
                        Store store = stores.get(storeName);
                        store.display_orders();
                        System.out.println("OK:display_completed");
                    }


                } else if (tokens[0].equals("request_item")) {
                    //System.out.println("store: " + tokens[1] + ", order: " + tokens[2] + ", item: " + tokens[3] + ", quantity: " + tokens[4] + ", unit_price: " + tokens[5]);
                    String storeName = tokens[1];
                    String orderID = tokens[2];
                    String itemName = tokens[3];
                    int quantity = Integer.parseInt(tokens[4]);
                    int unitPrice = Integer.parseInt(tokens[5]);

                    Store store = stores.get(storeName);
                    if (store == null) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }else {
                        Order order = store.getOrders().get(orderID);
                        if (order == null) {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        }else {
                            if (!store.getCatalog().containsKey(itemName)) {
                                System.out.println("ERROR:item_identifier_does_not_exist");
                            }else {
                                if (order.getOrderLines().containsKey(itemName)) {
                                    System.out.println("ERROR:item_already_ordered");
                                }else {
                                    order.request_item(itemName, quantity, unitPrice);
                                }
                            }
                        }
                    }


                } else if (tokens[0].equals("purchase_order")) {
                    //System.out.println("store: " + tokens[1] + ", order: " + tokens[2]);
                    String storeName = tokens[1];
                    String orderID = tokens[2];
                    Store store = stores.get(storeName);
                    if (store == null) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }else {
                        Order order = store.getOrders().get(orderID);
                        if (order == null) {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        }else{
                            Drone drone = order.getDrone();
                            if(drone.getFuel() < 1){
                                System.out.println("ERROR:drone_needs_fuel");
                            }else{
                                if (drone.getPilot() == null){
                                    System.out.println("ERROR:drone_needs_pilot");
                                }else {
                                    Customer customer = order.getCustomer();
                                    int order_weight = order.weight;
                                    int cost = order.cost;

                                    store.overLoads(orderID);
                                    customer.completeOrder(orderID, cost);
                                    store.completeOrder(orderID, cost);
                                    drone.deliverOrder(orderID, order_weight);
                                    drone.getPilot().deliverOrder();

                                    System.out.println("OK:change_completed");
                                }
                            }
                        }
                    }


                } else if (tokens[0].equals("cancel_order")) {
                    //System.out.println("store: " + tokens[1] + ", order: " + tokens[2]);
                    String storeName = tokens[1];
                    String orderID = tokens[2];
                    Store store = stores.get(storeName);
                    if (store == null) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }else {
                        Order order = store.getOrders().get(orderID);
                        if (order == null) {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        }else{
                            Drone drone = order.getDrone();
                            Customer customer = order.getCustomer();
                            drone.cancelOrder(orderID, order.weight);
                            customer.cancelOrder(orderID);
                            store.cancelOrder(orderID);
                            System.out.println("OK:change_completed");

                        }
                    }


                } else if (tokens[0].equals("transfer_order")) {
                    // System.out.println("store: " + tokens[1] + ", order: " + tokens[2] + ", new_drone: " + tokens[3]);
                    String storeName = tokens[1];
                    String orderID = tokens[2];
                    String droneID = tokens[3];
                    Store store = stores.get(storeName);
                    Order order = store.getOrders().get(orderID);
                    Drone olddrone = order.getDrone();
                    Drone newdrone = store.getDrones().get(droneID);


                    if (order.getDrone() == newdrone){
                        System.out.println("OK:new_drone_is_current_drone_no_change");
                    }else{
                        if (order.weight > newdrone.getRemainingCapacity()){
                            System.out.println("ERROR:new_drone_does_not_have_enough_capacity");
                        }else{
                            order.transfer_order(newdrone);
                            olddrone.transfer_order(newdrone, orderID, order.weight);
                            successfulTransfers++;
                            System.out.println("OK:change_completed");

                        }
                    }



                } else if (tokens[0].equals("display_efficiency")) {
                    // System.out.println("no parameters needed");
                    List<Store> sortedStores = new ArrayList<>(stores.values());
                    Collections.sort(sortedStores, Comparator.comparing(Store::getName)); // Sort by name
                    StringBuilder sb = new StringBuilder();

                    for (Store store : sortedStores) {

                        sb.append("name:").append(store.getName()).append(",purchases:").append(store.getPurchases()).
                                append(",overloads:").append(store.getOverloads() ).append(",transfers:").append(successfulTransfers);
                    }
                    System.out.println(sb.toString().trim());
                    System.out.println("OK:display_completed");

                } else if (tokens[0].equals("stop")) {
                    System.out.println("stop acknowledged");
                    break;

                } else {
                    System.out.println("command " + tokens[0] + " NOT acknowledged");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }

        System.out.println("simulation terminated");
        commandLineInput.close();
    }
}
