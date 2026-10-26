package com.uniti.cil.itta.inventory.common.model;

public enum LifecycleStatus {
    PLANNED, ACTIVE, MAINTENANCE, DECOMMISSIONED;

    public boolean isOperational() {
        return this == ACTIVE || this == MAINTENANCE;
    }
}
