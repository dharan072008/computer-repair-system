package com.example.landf.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lost_items")
public class LostItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Renamed from item_name to title for standard camelCase consistency with frontend
    private String title; 
    
    private String description;
    private String category;
    private String location;
    
    // Renamed from email to contact to match frontend name
    private String contact;
    
    // ADDED: Missing field from frontend form (LOST/FOUND)
    private String type; 
    
    // ADDED: Missing field from frontend form
    private String imageUrl; 

    // --- Constructors ---
    public LostItem() {
    }

    // --- Getters and Setters (Updated for new field names) ---

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    // Renamed getter/setter for 'title'
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    // Renamed getter/setter for 'contact'
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    // New getter/setter for 'type'
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    // New getter/setter for 'imageUrl'
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}