package com.assignment.people.model;

/**
 * Created by affandy on 25/08/2017.
 */

public class People {
    private long Id;
    private String name;
    private String description;
    private String dateCreated;
    private String dateUpdated;

    public People(long id, String strname, String strdescription, String strdateCreated, String strdateUpdated) {
        Id = id;
        name = strname;
        description = strdescription;
        dateCreated = strdateCreated;
        dateUpdated = strdateUpdated;
    }

    public People() {}

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

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

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Override
    public String toString() {
        return "People{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", dateUpdated='" + dateUpdated + '\'' +
                '}';
    }
}
