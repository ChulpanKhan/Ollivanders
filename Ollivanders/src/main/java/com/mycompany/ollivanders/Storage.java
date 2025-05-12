
package com.mycompany.ollivanders;

import jakarta.persistence.*;

@Entity
@Table(name = "storage")
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "object", length = 100)
    private String itemName;
    @Column(name = "type", length = 20)
    private String type; //core or wood
    @Column(name = "quantity")
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supply_id")
    private Supply supply;

    public Storage() {}

    public Storage(String itemName, String type, int quantity, Supply supply) {
        this.itemName = itemName;
        this.type = type;
        this.quantity = quantity;
        this.supply = supply;
    }
    
    //getters and setters
    public int getId(){
        return id;
    }
    
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
