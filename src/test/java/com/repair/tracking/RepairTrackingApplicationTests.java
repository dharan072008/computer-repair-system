package com.repair.tracking;

import com.repair.tracking.controller.RepairController;
import com.repair.tracking.dto.RepairStatsDTO;
import com.repair.tracking.model.Repair;
import com.repair.tracking.repository.RepairRepository;
import com.repair.tracking.service.RepairService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Automated Test Suite for Computer Repair and Service Tracking System.
 * Tests Spring Context loading, JPA CRUD operations, Service business logic, and Controller endpoints.
 */
@SpringBootTest
@ActiveProfiles("h2")
public class RepairTrackingApplicationTests {

    @Autowired
    private RepairRepository repairRepository;

    @Autowired
    private RepairService repairService;

    @Autowired
    private RepairController repairController;

    @BeforeEach
    void setUp() {
        repairRepository.deleteAll();
    }

    @Test
    @DisplayName("Verify Spring Application Context and Beans load successfully")
    void contextLoads() {
        assertThat(repairService).isNotNull();
        assertThat(repairRepository).isNotNull();
        assertThat(repairController).isNotNull();
    }

    @Test
    @DisplayName("Service Layer: Create and retrieve a repair request")
    void testCreateAndFindRepair() {
        Repair repair = new Repair("Alice Smith", "9876543210", "MacBook Pro M2",
                "Screen flickering and battery drain", "Technician Alex", "REGISTERED", LocalDate.now());
        Repair saved = repairService.createRepair(repair);

        assertThat(saved.getId()).isNotNull();
        Optional<Repair> found = repairService.getRepairById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getCustomerName()).isEqualTo("Alice Smith");
        assertThat(found.get().getRepairStatus()).isEqualTo("REGISTERED");
    }

    @Test
    @DisplayName("Service Layer: Update status and technician assignment")
    void testUpdateRepair() {
        Repair repair = new Repair("Bob Jones", "9123456780", "Dell XPS 15",
                "Overheating fan noise", null, "REGISTERED", LocalDate.now());
        Repair saved = repairService.createRepair(repair);

        Repair updateDetails = new Repair();
        updateDetails.setRepairStatus("IN_PROGRESS");
        updateDetails.setTechnician("Technician Rahul");

        Optional<Repair> updated = repairService.updateRepair(saved.getId(), updateDetails);
        assertThat(updated).isPresent();
        assertThat(updated.get().getRepairStatus()).isEqualTo("IN_PROGRESS");
        assertThat(updated.get().getTechnician()).isEqualTo("Technician Rahul");
    }

    @Test
    @DisplayName("Service Layer: Delete repair and verify removal")
    void testDeleteRepair() {
        Repair repair = new Repair("Carol White", "9988776655", "Lenovo ThinkPad",
                "Spilled water on keyboard", "Technician Priya", "REGISTERED", LocalDate.now());
        Repair saved = repairService.createRepair(repair);

        boolean deleted = repairService.deleteRepair(saved.getId());
        assertThat(deleted).isTrue();
        assertThat(repairService.getRepairById(saved.getId())).isEmpty();
    }

    @Test
    @DisplayName("Service Layer: Calculate dashboard aggregated statistics")
    void testDashboardStatistics() {
        repairService.createRepair(new Repair("Customer 1", "1111", "Laptop", "Issue", "Tech 1", "REGISTERED", LocalDate.now()));
        repairService.createRepair(new Repair("Customer 2", "2222", "Desktop", "Issue", "Tech 2", "IN_PROGRESS", LocalDate.now()));
        repairService.createRepair(new Repair("Customer 3", "3333", "Tablet", "Issue", "Tech 1", "COMPLETED", LocalDate.now()));
        repairService.createRepair(new Repair("Customer 4", "4444", "Laptop", "Issue", "Tech 2", "WAITING_FOR_PARTS", LocalDate.now()));

        RepairStatsDTO stats = repairService.getStatistics();
        assertThat(stats.getTotal()).isEqualTo(4);
        assertThat(stats.getRegistered()).isEqualTo(1);
        assertThat(stats.getInProgress()).isEqualTo(1);
        assertThat(stats.getCompleted()).isEqualTo(1);
        assertThat(stats.getWaitingForParts()).isEqualTo(1);
        assertThat(stats.getCancelled()).isEqualTo(0);
    }

    @Test
    @DisplayName("Controller Layer: POST /api/repairs creates repair and returns 201 CREATED")
    void testControllerCreateRepair() {
        Repair repair = new Repair("David Lee", "9811223344", "HP Envy 13",
                "Blue screen on startup", "Technician Sarah", "REGISTERED", LocalDate.now());

        ResponseEntity<?> response = repairController.createRepair(repair);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isInstanceOf(Repair.class);
        Repair created = (Repair) response.getBody();
        assertThat(created.getId()).isNotNull();
        assertThat(created.getCustomerName()).isEqualTo("David Lee");
    }

    @Test
    @DisplayName("Controller Layer: Validation rejects missing customer name with 400 BAD_REQUEST")
    void testControllerValidation() {
        Repair invalid = new Repair(null, "9811223344", "HP Envy 13", "Issue", null, null, null);
        ResponseEntity<?> response = repairController.createRepair(invalid);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Controller Layer: GET /api/repairs/stats returns aggregated metrics")
    void testControllerGetStats() {
        repairService.createRepair(new Repair("Test User", "5555", "PC", "Boot error", "Tech A", "REGISTERED", LocalDate.now()));

        ResponseEntity<RepairStatsDTO> response = repairController.getStatistics();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTotal()).isEqualTo(1);
        assertThat(response.getBody().getRegistered()).isEqualTo(1);
    }

    @Test
    @DisplayName("Controller Layer: DELETE /api/repairs/{id} returns 200 OK or 404 NOT_FOUND")
    void testControllerDeleteRepair() {
        Repair saved = repairService.createRepair(new Repair("Delete Me", "0000", "Monitor", "Dead pixels", null, "REGISTERED", LocalDate.now()));

        ResponseEntity<?> deleteResp = repairController.deleteRepair(saved.getId());
        assertThat(deleteResp.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<?> notFoundResp = repairController.deleteRepair(99999L);
        assertThat(notFoundResp.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
