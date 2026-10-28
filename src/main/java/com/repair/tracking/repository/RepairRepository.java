package com.repair.tracking.repository;

import com.repair.tracking.model.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for the Repair entity.
 * Provides out-of-the-box CRUD operations and custom query methods.
 */
@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {

    // Retrieve repairs filtered by status
    List<Repair> findByRepairStatusIgnoreCase(String repairStatus);

    // Retrieve all repairs in descending order of ID (most recent first)
    List<Repair> findAllByOrderByIdDesc();

    // Count repairs by status for dashboard analytics
    long countByRepairStatusIgnoreCase(String repairStatus);
}
