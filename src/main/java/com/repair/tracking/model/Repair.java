package com.repair.tracking.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entity representing a Computer Repair Request.
 * Designed for OOPJ: Demonstrates OOP encapsulation with private fields,
 * explicit constructors, and public getter/setter methods.
 */
@Entity
@Table(name = "repairs")
public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "device_type", nullable = false, length = 50)
    private String deviceType;

    @Column(name = "problem_description", columnDefinition = "TEXT")
    private String problemDescription;

    @Column(name = "technician", length = 100)
    private String technician;

    @Column(name = "repair_status", length = 30)
    private String repairStatus = "REGISTERED";

    @Column(name = "repair_date")
    private LocalDate repairDate;

    // Default No-Arg Constructor (Required by JPA)
    public Repair() {
        this.repairStatus = "REGISTERED";
        this.repairDate = LocalDate.now();
    }

    // Parameterized Constructor (Demonstrating OOP Constructor Overloading)
    public Repair(String customerName, String phone, String deviceType, 
                  String problemDescription, String technician, 
                  String repairStatus, LocalDate repairDate) {
        this.customerName = customerName;
        this.phone = phone;
        this.deviceType = deviceType;
        this.problemDescription = problemDescription;
        this.technician = technician;
        this.repairStatus = (repairStatus != null && !repairStatus.trim().isEmpty()) ? repairStatus.toUpperCase() : "REGISTERED";
        this.repairDate = (repairDate != null) ? repairDate : LocalDate.now();
    }

    // --- Encapsulation: Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }

    public String getRepairStatus() {
        return repairStatus;
    }

    public void setRepairStatus(String repairStatus) {
        this.repairStatus = (repairStatus != null) ? repairStatus.toUpperCase() : "REGISTERED";
    }

    public LocalDate getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(LocalDate repairDate) {
        this.repairDate = repairDate;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", phone='" + phone + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", technician='" + technician + '\'' +
                ", repairStatus='" + repairStatus + '\'' +
                ", repairDate=" + repairDate +
                '}';
    }
}
