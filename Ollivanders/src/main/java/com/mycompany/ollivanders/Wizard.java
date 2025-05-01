
package com.mycompany.ollivanders;

public class Wizard {
    private int id; 
    private String name;
    private Wand wand;

    public Wizard(String name, Wand wand) {
        this.name = name;
        this.wand = wand;
    }

    //getters and settters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Wand getWand() {
        return wand;
    }
    public void setWand(Wand wand) {
        this.wand = wand;
    }

}
