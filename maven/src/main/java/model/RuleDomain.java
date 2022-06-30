package model;

import java.io.Serializable;

public class RuleDomain implements IRuleDomain, Serializable {
    private String name;

    public RuleDomain(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
