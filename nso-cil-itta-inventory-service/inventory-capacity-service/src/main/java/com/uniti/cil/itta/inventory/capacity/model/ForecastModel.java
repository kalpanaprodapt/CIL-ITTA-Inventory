package com.uniti.cil.itta.inventory.capacity.model;

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
 * Forecast Model - Capacity Planning domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_forecast_model",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_forecast_model_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_forecast_model_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_forecast_model_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_forecast_model_ext", columnList = "external_ref")
        })
public class ForecastModel extends BaseEntity {

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

    @Column(name = "model_name", nullable = false, length = 255)
    private String modelName;

    @Enumerated(EnumType.STRING)
    @Column(name = "algorithm", nullable = true, length = 40)
    private ForecastModelAlgorithm algorithm;

    @Column(name = "trained_on", nullable = true)
    private Instant trainedOn;

    @Column(name = "mape", nullable = true, precision = 18, scale = 4)
    private BigDecimal mape;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private ForecastModelCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    public ForecastModel() {
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

    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }

    public ForecastModelAlgorithm getAlgorithm() { return algorithm; }
    public void setAlgorithm(ForecastModelAlgorithm algorithm) { this.algorithm = algorithm; }

    public Instant getTrainedOn() { return trainedOn; }
    public void setTrainedOn(Instant trainedOn) { this.trainedOn = trainedOn; }

    public BigDecimal getMape() { return mape; }
    public void setMape(BigDecimal mape) { this.mape = mape; }

    public ForecastModelCriticality getCriticality() { return criticality; }
    public void setCriticality(ForecastModelCriticality criticality) { this.criticality = criticality; }

    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ForecastModel other)) {
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
        return "ForecastModel{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "modelName=" + modelName + ", " +
                "algorithm=" + algorithm + ", " +
                "trainedOn=" + trainedOn + ", " +
                "mape=" + mape + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + "}";
    }
}
