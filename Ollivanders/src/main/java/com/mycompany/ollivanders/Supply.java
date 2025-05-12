
package com.mycompany.ollivanders;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "supply")
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "date")
    private Timestamp supplyDate;
    
    public Supply() {}

    public Supply(Timestamp supplyDate) {
        this.supplyDate = supplyDate;
    }
    
    //getters and setters
    public int getId() {
        return id;
    }
    
    public Timestamp getSupplyDate() {
        return supplyDate;
    }
    public void setSupplyDate(Timestamp supplyDate){
        this.supplyDate = supplyDate;
    }
}
