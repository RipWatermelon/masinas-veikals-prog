package com.project.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.util.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private static final String FILE_PATH = "src/main/resources/Orders.json";
    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/get-order")
    public List<Map<String, Object>> getAllOrders() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        return mapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {});
    }

    @PostMapping("/add-order")
    public List<Map<String, Object>> addOrder(@RequestBody Map<String, Object> order) throws IOException {
        List<Map<String, Object>> orders = getAllOrders();
        int newId = orders.stream().mapToInt(o -> o.get("orderID") == null ? 0 : (int) o.get("orderID")).max().orElse(0) + 1;
        order.put("orderID", newId);
        orders.add(order);
        mapper.writeValue(new File(FILE_PATH), orders);
        return orders;
    }

    @DeleteMapping("/remove-order/{id}")
    public List<Map<String, Object>> removeOrder(@PathVariable int id) throws IOException {
        List<Map<String, Object>> orders = getAllOrders();
        orders.removeIf(o -> Objects.equals(o.get("orderID"), id));
        mapper.writeValue(new File(FILE_PATH), orders);
        return orders;
    }
}

