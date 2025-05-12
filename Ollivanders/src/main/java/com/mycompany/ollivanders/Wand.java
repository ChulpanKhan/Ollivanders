
package com.mycompany.ollivanders;

import jakarta.persistence.*;

@Entity
@Table(name = "wand")
public class Wand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "wood", length = 100)
    private String wood;
    @Column(name = "core", length = 100)
    private String core;
    @Column(name = "status", length = 20)
    private String status;

    public Wand() {
    }

    public Wand(String core, String wood, String status) {
        this.core = core;
        this.wood = wood;
        this.status = status;
    }

    //getters and setters
    public int getId(){
        return id;
    }
    
    public String getCore(){
        return core;
    }
    public void setCore(String core){
        this.core = core;
    }
    
    public String getWood(){
        return wood;
    }
    public void setWood(String wood){
        this.wood = wood;
    }
    
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
}
