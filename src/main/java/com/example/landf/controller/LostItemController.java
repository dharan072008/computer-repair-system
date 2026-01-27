package com.example.landf.controller;

import com.example.landf.model.LostItem;
import com.example.landf.repository.LostItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Ensure this path is used in the frontend fetch call
@RequestMapping("/api/lostitems") 
@CrossOrigin
public class LostItemController {

    @Autowired
    private LostItemRepository repository;

    @GetMapping
    public List<LostItem> getAllItems() {
        System.out.println("Received GET /api/lostitems request.");
        return repository.findAll();
    }

    @PostMapping
    public LostItem addItem(@RequestBody LostItem item) {
        // Log the received item title to confirm success
        System.out.println("Received POST /api/lostitems request for: " + item.getTitle() + " (" + item.getType() + ")");
        return repository.save(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        System.out.println("Received DELETE /api/lostitems/" + id + " request.");
        repository.deleteById(id);
    }
}