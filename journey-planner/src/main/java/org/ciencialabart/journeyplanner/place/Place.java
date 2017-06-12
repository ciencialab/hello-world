package org.ciencialabart.journeyplanner.place;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Place {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    
    private String name;

    public Place() {}

    public Place(String name) {
        this.name = name;
    }

    public Place(long id, String name) {
        this(name);
        
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
}
