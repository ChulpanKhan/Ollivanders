
package com.mycompany.ollivanders;

public class Storage {

    private int id;
    private String itemName;
    private String type; //core or wood
    private int quantity;    
    private Supply supply;

    public Storage() {}

    public Storage(String itemName, String type, int quantity, Supply supply) {
        this.itemName = itemName;
        this.type = type;
        this.quantity = quantity;
        this.supply = supply;
    }
    
    //getters and setters
    public String getItemName(){
        return itemName;
    }
    public void setItemName(String itemName){
        this.itemName = itemName;
    }
    
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
    
    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    
    public Supply getSupply() {
        return supply;
    }
    public void setSupply(Supply supply){
        this.supply = supply;
    }
}
