
package com.mycompany.ollivanders;

public class Wand {

    private int id;
    private String core;
    private String wood;
    private String status;

    public Wand() {
    }

    public Wand(String core, String wood) {
        this.core = core;
        this.wood = wood;
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
