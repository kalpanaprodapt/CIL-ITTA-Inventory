package com.uniti.cil.itta.inventory.ipam.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for DnsRecord. */
public final class DnsRecordFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private DnsRecordFixtures() {
    }

    public static DnsRecordCreateRequest createRequest() {
        DnsRecordCreateRequest request = new DnsRecordCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setZoneName("zoneName-1");
        request.setRecordType(DnsRecordRecordType.A);
        request.setRecordValue("recordValue-1");
        request.setTtl(11);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(DnsRecordCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setCode("DNSREC-0001");
        request.setName("Dns Record one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static DnsRecord entity() {
        DnsRecord entity = new DnsRecord();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setZoneName("zoneName-2");
        entity.setRecordType(DnsRecordRecordType.AAAA);
        entity.setRecordValue("recordValue-2");
        entity.setTtl(12);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(DnsRecordCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setCode("DNSREC-0002");
        entity.setName("Dns Record two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static DnsRecordResponse response() {
        DnsRecordResponse response = new DnsRecordResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("DNSREC-0001");
        response.setName("Dns Record one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static DnsRecordSearchCriteria criteria() {
        DnsRecordSearchCriteria criteria = new DnsRecordSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
