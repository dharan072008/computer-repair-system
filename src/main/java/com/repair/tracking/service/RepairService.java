package com.repair.tracking.service;

import com.repair.tracking.dto.RepairStatsDTO;
import com.repair.tracking.model.Repair;
import com.repair.tracking.repository.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Layer for Repair Management.
 * Encapsulates business logic, validation, and dashboard metrics calculation.
 */
@Service
public class RepairService {

    private final RepairRepository repairRepository;

    @Autowired
    public RepairService(RepairRepository repairRepository) {
        this.repairRepository = repairRepository;
    }

    /**
     * Retrieve all repairs, optionally filtered by status.
     */
    public List<Repair> getAllRepairs(String status) {
        if (status != null && !status.trim().isEmpty() && !status.equalsIgnoreCase("ALL")) {
            return repairRepository.findByRepairStatusIgnoreCase(status.trim());
        }
        return repairRepository.findAllByOrderByIdDesc();
    }

    /**
     * Retrieve a specific repair by its ID.
     */
    public Optional<Repair> getRepairById(Long id) {
        return repairRepository.findById(id);
    }

    /**
     * Register a new repair. Defaults status to REGISTERED if omitted.
     */
    public Repair createRepair(Repair repair) {
        if (repair.getRepairStatus() == null || repair.getRepairStatus().trim().isEmpty()) {
            repair.setRepairStatus("REGISTERED");
        } else {
            repair.setRepairStatus(repair.getRepairStatus().toUpperCase());
        }

        if (repair.getRepairDate() == null) {
            repair.setRepairDate(LocalDate.now());
        }

        return repairRepository.save(repair);
    }

    /**
     * Update an existing repair record.
     */
    public Optional<Repair> updateRepair(Long id, Repair updatedRepair) {
        return repairRepository.findById(id).map(existing -> {
            if (updatedRepair.getCustomerName() != null) {
                existing.setCustomerName(updatedRepair.getCustomerName());
            }
            if (updatedRepair.getPhone() != null) {
                existing.setPhone(updatedRepair.getPhone());
            }
            if (updatedRepair.getDeviceType() != null) {
                existing.setDeviceType(updatedRepair.getDeviceType());
            }
            if (updatedRepair.getProblemDescription() != null) {
                existing.setProblemDescription(updatedRepair.getProblemDescription());
            }
            if (updatedRepair.getTechnician() != null) {
                existing.setTechnician(updatedRepair.getTechnician());
            }
            if (updatedRepair.getRepairStatus() != null && !updatedRepair.getRepairStatus().trim().isEmpty()) {
                existing.setRepairStatus(updatedRepair.getRepairStatus().toUpperCase());
            }
            if (updatedRepair.getRepairDate() != null) {
                existing.setRepairDate(updatedRepair.getRepairDate());
            }
            return repairRepository.save(existing);
        });
    }

    /**
     * Delete a repair record by ID.
     */
    public boolean deleteRepair(Long id) {
        if (repairRepository.existsById(id)) {
            repairRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Calculate and return aggregated statistics for the dashboard.
     */
    public RepairStatsDTO getStatistics() {
        long total = repairRepository.count();
        long registered = repairRepository.countByRepairStatusIgnoreCase("REGISTERED");
        long inProgress = repairRepository.countByRepairStatusIgnoreCase("IN_PROGRESS");
        long waitingForParts = repairRepository.countByRepairStatusIgnoreCase("WAITING_FOR_PARTS");
        long completed = repairRepository.countByRepairStatusIgnoreCase("COMPLETED");
        long cancelled = repairRepository.countByRepairStatusIgnoreCase("CANCELLED");

        return new RepairStatsDTO(total, registered, inProgress, waitingForParts, completed, cancelled);
    }
}
