package com.uniti.cil.itta.inventory.change.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import java.util.UUID;

/** Lightweight projection of a Work Order for pick-lists. */
public class WorkOrderSummary {

    private UUID id;
    private String code;
    private String name;
    private LifecycleStatus status;

    public WorkOrderSummary() {
    }

    public WorkOrderSummary(UUID id, String code, String name, LifecycleStatus status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LifecycleStatus getStatus() { return status; }
    public void setStatus(LifecycleStatus status) { this.status = status; }
}
