package com.uniti.cil.itta.inventory.radio.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import java.util.UUID;

/** Lightweight projection of a Sector for pick-lists. */
public class SectorSummary {

    private UUID id;
    private String code;
    private String name;
    private LifecycleStatus status;

    public SectorSummary() {
    }

    public SectorSummary(UUID id, String code, String name, LifecycleStatus status) {
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
