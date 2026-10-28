package com.repair.tracking.dto;

/**
 * Data Transfer Object (DTO) for Dashboard Statistics.
 * Decouples entity representation from analytics aggregate data.
 */
public class RepairStatsDTO {
    private long total;
    private long registered;
    private long inProgress;
    private long waitingForParts;
    private long completed;
    private long cancelled;

    public RepairStatsDTO() {
    }

    public RepairStatsDTO(long total, long registered, long inProgress, 
                          long waitingForParts, long completed, long cancelled) {
        this.total = total;
        this.registered = registered;
        this.inProgress = inProgress;
        this.waitingForParts = waitingForParts;
        this.completed = completed;
        this.cancelled = cancelled;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getRegistered() {
        return registered;
    }

    public void setRegistered(long registered) {
        this.registered = registered;
    }

    public long getInProgress() {
        return inProgress;
    }

    public void setInProgress(long inProgress) {
        this.inProgress = inProgress;
    }

    public long getWaitingForParts() {
        return waitingForParts;
    }

    public void setWaitingForParts(long waitingForParts) {
        this.waitingForParts = waitingForParts;
    }

    public long getCompleted() {
        return completed;
    }

    public void setCompleted(long completed) {
        this.completed = completed;
    }

    public long getCancelled() {
        return cancelled;
    }

    public void setCancelled(long cancelled) {
        this.cancelled = cancelled;
    }
}
