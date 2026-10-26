package com.uniti.cil.itta.inventory.geography.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Country search endpoint. */
public class CountrySearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String isoCode;
    private Integer isoNumericMin;
    private Integer isoNumericMax;
    private String dialCode;
    private String currency;
    private String regulatoryBody;
    private String timezone;
    private String region;
    private String owner;
    private List<CountryCriticality> criticality;
    private String costCenter;

    public String getQ() { return q; }
    public void setQ(String q) { this.q = q; }
    public List<LifecycleStatus> getStatus() { return status; }
    public void setStatus(List<LifecycleStatus> status) { this.status = status; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getExternalRef() { return externalRef; }
    public void setExternalRef(String externalRef) { this.externalRef = externalRef; }
    public String getIsoCode() { return isoCode; }
    public void setIsoCode(String isoCode) { this.isoCode = isoCode; }
    public Integer getIsoNumericMin() { return isoNumericMin; }
    public void setIsoNumericMin(Integer isoNumericMin) { this.isoNumericMin = isoNumericMin; }
    public Integer getIsoNumericMax() { return isoNumericMax; }
    public void setIsoNumericMax(Integer isoNumericMax) { this.isoNumericMax = isoNumericMax; }
    public String getDialCode() { return dialCode; }
    public void setDialCode(String dialCode) { this.dialCode = dialCode; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getRegulatoryBody() { return regulatoryBody; }
    public void setRegulatoryBody(String regulatoryBody) { this.regulatoryBody = regulatoryBody; }
    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<CountryCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<CountryCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
