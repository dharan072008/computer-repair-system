package com.example.landf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.landf.model.LostItem;

public interface LostItemRepository extends JpaRepository<LostItem, Long> {
}
