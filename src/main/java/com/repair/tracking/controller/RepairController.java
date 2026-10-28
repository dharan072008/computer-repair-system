package com.repair.tracking.controller;

import com.repair.tracking.dto.RepairStatsDTO;
import com.repair.tracking.model.Repair;
import com.repair.tracking.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller providing HTTP endpoints for Repair management.
 * CORS is enabled to allow smooth communication from the web frontend and Postman.
 */
@RestController
@RequestMapping("/api/repairs")
@CrossOrigin(origins = "*")
public class RepairController {

    private final RepairService repairService;

    @Autowired
    public RepairController(RepairService repairService) {
        this.repairService = repairService;
    }

    /**
     * GET /api/repairs
     * Retrieve all repair records, with optional status filter (e.g., ?status=IN_PROGRESS).
     */
    @GetMapping
    public ResponseEntity<List<Repair>> getAllRepairs(@RequestParam(required = false) String status) {
        List<Repair> repairs = repairService.getAllRepairs(status);
        return ResponseEntity.ok(repairs);
    }

    /**
     * GET /api/repairs/{id}
     * Retrieve a single repair record by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getRepairById(@PathVariable Long id) {
        return repairService.getRepairById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    Map<String, Object> error = new HashMap<>();
                    error.put("status", HttpStatus.NOT_FOUND.value());
                    error.put("message", "Repair record with ID " + id + " not found.");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                });
    }

    /**
     * POST /api/repairs
     * Register a new computer repair request.
     */
    @PostMapping
    public ResponseEntity<?> createRepair(@RequestBody Repair repair) {
        // Validation for required fields
        if (repair.getCustomerName() == null || repair.getCustomerName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Customer name is required.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        if (repair.getDeviceType() == null || repair.getDeviceType().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Device type is required.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        Repair saved = repairService.createRepair(repair);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * PUT /api/repairs/{id}
     * Update an existing repair record.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRepair(@PathVariable Long id, @RequestBody Repair updatedRepair) {
        return repairService.updateRepair(id, updatedRepair)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    Map<String, Object> error = new HashMap<>();
                    error.put("status", HttpStatus.NOT_FOUND.value());
                    error.put("message", "Cannot update: Repair record with ID " + id + " does not exist.");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                });
    }

    /**
     * DELETE /api/repairs/{id}
     * Delete a repair record by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRepair(@PathVariable Long id) {
        boolean deleted = repairService.deleteRepair(id);
        if (deleted) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Repair record with ID " + id + " successfully deleted.");
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("status", HttpStatus.NOT_FOUND.value());
            error.put("message", "Cannot delete: Repair record with ID " + id + " does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * GET /api/repairs/stats
     * Return aggregated counts for Dashboard summary cards.
     */
    @GetMapping("/stats")
    public ResponseEntity<RepairStatsDTO> getStatistics() {
        RepairStatsDTO stats = repairService.getStatistics();
        return ResponseEntity.ok(stats);
    }
}
