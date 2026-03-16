package com.project.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.util.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/listings")
public class ListingController {
    private static final String FILE_PATH = "src/main/resources/Listings.json";
    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/get-listing")
    public List<Map<String, Object>> getAllListings() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        return mapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {});
    }

    @PostMapping("/add-listing")
    public List<Map<String, Object>> addListing(@RequestBody Map<String, Object> listing) throws IOException {
        List<Map<String, Object>> listings = getAllListings();
        int newId = listings.stream().mapToInt(l -> l.get("listingID") == null ? 0 : (int) l.get("listingID")).max().orElse(0) + 1;
        listing.put("listingID", newId);
        listings.add(listing);
        mapper.writeValue(new File(FILE_PATH), listings);
        return listings;
    }

    @DeleteMapping("/remove-listing/{id}")
    public List<Map<String, Object>> removeListing(@PathVariable int id) throws IOException {
        List<Map<String, Object>> listings = getAllListings();
        listings.removeIf(l -> Objects.equals(l.get("listingID"), id));
        mapper.writeValue(new File(FILE_PATH), listings);
        return listings;
    }
}

