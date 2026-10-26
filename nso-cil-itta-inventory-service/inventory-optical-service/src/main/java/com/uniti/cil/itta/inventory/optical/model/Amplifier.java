package com.uniti.cil.itta.inventory.optical.model;

import com.uniti.cil.itta.inventory.common.model.BaseEntity;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/**
 * Amplifier - Optical domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_amplifier",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_amplifier_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_amplifier_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_amplifier_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_amplifier_ext", columnList = "external_ref")
        })
public class Amplifier extends BaseEntity {

    @Column(name = "code", nullable = false, length = 255)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", nullable = true, length = 4000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private LifecycleStatus status;

    @Column(name = "external_ref", nullable = true, length = 255)
    private String externalRef;

    @Column(name = "system_id", nullable = false)
    private UUID systemId;

    @Enumerated(EnumType.STRING)
    @Column(name = "amp_type", nullable = true, length = 40)
    private AmplifierAmpType ampType;

    @Column(name = "gain_db", nullable = false, precision = 18, scale = 4)
    private BigDecimal gainDb;

    @Column(name = "output_power_dbm", nullable = true, precision = 18, scale = 4)
    private BigDecimal outputPowerDbm;

    @Column(name = "noise_figure_db", nullable = true, precision = 18, scale = 4)
    private BigDecimal noiseFigureDb;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private AmplifierLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private AmplifierCriticality criticality;

    public Amplifier() {
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LifecycleStatus getStatus() { return status; }
    public void setStatus(LifecycleStatus status) { this.status = status; }

    public String getExternalRef() { return externalRef; }
    public void setExternalRef(String externalRef) { this.externalRef = externalRef; }

    public UUID getSystemId() { return systemId; }
    public void setSystemId(UUID systemId) { this.systemId = systemId; }

    public AmplifierAmpType getAmpType() { return ampType; }
    public void setAmpType(AmplifierAmpType ampType) { this.ampType = ampType; }

    public BigDecimal getGainDb() { return gainDb; }
    public void setGainDb(BigDecimal gainDb) { this.gainDb = gainDb; }

    public BigDecimal getOutputPowerDbm() { return outputPowerDbm; }
    public void setOutputPowerDbm(BigDecimal outputPowerDbm) { this.outputPowerDbm = outputPowerDbm; }

    public BigDecimal getNoiseFigureDb() { return noiseFigureDb; }
    public void setNoiseFigureDb(BigDecimal noiseFigureDb) { this.noiseFigureDb = noiseFigureDb; }

    public AmplifierLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(AmplifierLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public AmplifierCriticality getCriticality() { return criticality; }
    public void setCriticality(AmplifierCriticality criticality) { this.criticality = criticality; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Amplifier other)) {
            return false;
        }
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Amplifier{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "systemId=" + systemId + ", " +
                "ampType=" + ampType + ", " +
                "gainDb=" + gainDb + ", " +
                "outputPowerDbm=" + outputPowerDbm + ", " +
                "noiseFigureDb=" + noiseFigureDb + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + "}";
    }
}
