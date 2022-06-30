package model;

import java.io.Serializable;

public class Source implements ISource, Serializable {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public Source(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
    
}
