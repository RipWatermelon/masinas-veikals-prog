package com.project.demo.model;

import jakarta.persistence.*;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderID;
    private String phoneNo;
    private String address;

    @ManyToOne
    @JoinColumn(name = "listingID")
    private Listing listing;

    public Listing getListing() {
    return listing;
}

public void setListing(Listing listing) {
    this.listing = listing;
}

// Getters and setters
}
