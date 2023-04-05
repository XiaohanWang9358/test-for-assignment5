package edu.gatech.cs6310;

public class Item {
    private String itemName;
    private int itemWeight;


    public Item(String itemName, int itemWeight){
        this.itemName = itemName;
        this.itemWeight = itemWeight;
    }

    public String getName(){
        return itemName;
    }

    public int getWeight(){
        return itemWeight;
    }
}
