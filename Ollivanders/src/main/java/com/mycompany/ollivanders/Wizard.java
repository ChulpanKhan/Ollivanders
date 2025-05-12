
package com.mycompany.ollivanders;

import jakarta.persistence.*;

@Entity
@Table(name = "wizard")
public class Wizard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", length = 100)
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wand_id")
    private Wand wand;

    public Wizard() {
    }

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
