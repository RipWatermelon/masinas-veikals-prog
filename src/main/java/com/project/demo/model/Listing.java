package com.project.demo.model;

import jakarta.persistence.*;

@Entity
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer listingID;
    private String vehicleType;
    private Boolean isInsured;
    private String condition;
    private Integer year;
    private String brand;
    private String imageLink;

    public Integer getListingID() {
    return listingID;
}

public void setListingID(Integer listingID) {
    this.listingID = listingID;
}

// Getters and setters
}
