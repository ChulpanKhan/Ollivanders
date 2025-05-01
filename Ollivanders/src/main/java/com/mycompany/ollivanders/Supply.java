
package com.mycompany.ollivanders;

import java.time.LocalDate;

public class Supply {

    private int id;
    private LocalDate supplyDate;
    
    public Supply() {}

    public Supply(LocalDate supplyDate) {
        this.supplyDate = supplyDate;
    }
    
    //getters and setters
    public int getId() {
        return id;
    }
    
    public LocalDate getSupplyDate() {
        return supplyDate;
    }
    public void setSupplyDate(LocalDate supplyDate){
        this.supplyDate = supplyDate;
    }
}
