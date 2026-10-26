package com.uniti.cil.itta.inventory.switching.model;

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
 * Bgp Session - L2 and L3 Routing domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_bgp_session",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_bgp_session_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_bgp_session_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_bgp_session_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_bgp_session_ext", columnList = "external_ref")
        })
public class BgpSession extends BaseEntity {

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

    @Column(name = "local_device_id", nullable = false)
    private UUID localDeviceId;

    @Column(name = "peer_address", nullable = false, length = 255)
    private String peerAddress;

    @Column(name = "local_asn", nullable = false)
    private Long localAsn;

    @Column(name = "remote_asn", nullable = false)
    private Long remoteAsn;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_state", nullable = true, length = 40)
    private BgpSessionSessionState sessionState;

    @Column(name = "prefix_limit", nullable = true)
    private Integer prefixLimit;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private BgpSessionCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    public BgpSession() {
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

    public UUID getLocalDeviceId() { return localDeviceId; }
    public void setLocalDeviceId(UUID localDeviceId) { this.localDeviceId = localDeviceId; }

    public String getPeerAddress() { return peerAddress; }
    public void setPeerAddress(String peerAddress) { this.peerAddress = peerAddress; }

    public Long getLocalAsn() { return localAsn; }
    public void setLocalAsn(Long localAsn) { this.localAsn = localAsn; }

    public Long getRemoteAsn() { return remoteAsn; }
    public void setRemoteAsn(Long remoteAsn) { this.remoteAsn = remoteAsn; }

    public BgpSessionSessionState getSessionState() { return sessionState; }
    public void setSessionState(BgpSessionSessionState sessionState) { this.sessionState = sessionState; }

    public Integer getPrefixLimit() { return prefixLimit; }
    public void setPrefixLimit(Integer prefixLimit) { this.prefixLimit = prefixLimit; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public BgpSessionCriticality getCriticality() { return criticality; }
    public void setCriticality(BgpSessionCriticality criticality) { this.criticality = criticality; }

    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BgpSession other)) {
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
        return "BgpSession{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "localDeviceId=" + localDeviceId + ", " +
                "peerAddress=" + peerAddress + ", " +
                "localAsn=" + localAsn + ", " +
                "remoteAsn=" + remoteAsn + ", " +
                "sessionState=" + sessionState + ", " +
                "prefixLimit=" + prefixLimit + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + "}";
    }
}
