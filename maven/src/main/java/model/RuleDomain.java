package model;

public class RuleDomain implements IRuleDomain {
    private String name;
    private String description;

    public RuleDomain(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean update(IRuleDomain updateData) {

        //setName(updateData.getName()); // primary key not changeable
        setDescription(updateData.getDescription());

        return true;
    }
}
