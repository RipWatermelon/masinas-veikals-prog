package com.project.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.util.*;
import com.fasterxml.jackson.core.type.TypeReference; //import for JS->JSON link
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController //defines that this is a controller
@RequestMapping("/api/listings") //prefix
public class ListingController {
    private static final String FILE_PATH = "src/main/resources/Listings.json"; // link to json file, static final=never changes
    private final ObjectMapper mapper = new ObjectMapper(); //converts java objects -> JSON

    @GetMapping("/get-listing")
    public List<Map<String, Object>> getAllListings() throws IOException { //returns a map of every listing
        File file = new File(FILE_PATH); //overwrites file every time
        if (!file.exists()) return new ArrayList<>(); //return empty list if it doesnt exist
        return mapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {}); //mapper converts json to java objects
    }

    @PostMapping("/add-listing") //responds to add listing requests
    public List<Map<String, Object>> addListing(@RequestBody Map<String, Object> listing) throws IOException { //takes json and turns it into a Map
        List<Map<String, Object>> listings = getAllListings(); //loads everything that exists in, then adds
        int newId = listings.stream().mapToInt(l -> l.get("listingID") == null ? 0 : (int) l.get("listingID")).max().orElse(0) + 1; // loop thru all the listings, grab each ID as an integer, find the highest id, (if theres no max then default to 0
        listing.put("listingID", newId); // and then +1 to the highest ID
        listings.add(listing);
        mapper.writeValue(new File(FILE_PATH), listings); //write the entire updated list to the JSON file, and overwrite
        return listings; //send the full thing back as API response
    }

    @DeleteMapping("/remove-listing/{id}") //responds to that specific ID we input
    public List<Map<String, Object>> removeListing(@PathVariable int id) throws IOException { // @PathVariable grabs the id from the URL and puts it into the id variable
        List<Map<String, Object>> listings = getAllListings();
        listings.removeIf(l -> Objects.equals(l.get("listingID"), id)); //removes listing if the ID matches the one we inputted
        mapper.writeValue(new File(FILE_PATH), listings); //saves the smaller list back to the file
        return listings; //return updated list as the response
    }
}

