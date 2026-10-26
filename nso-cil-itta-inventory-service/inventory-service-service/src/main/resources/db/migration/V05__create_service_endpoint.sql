-- Service Endpoint (Services)
create table inv_service_endpoint (
    id uuid primary key,
    tenant_id varchar(64) not null,
    version bigint not null default 0,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    created_by varchar(128),
    updated_by varchar(128),
    code varchar(255) not null,
    name varchar(255) not null,
    description varchar(4000),
    status varchar(40) not null,
    external_ref varchar(255),
    service_id uuid not null,
    site_id uuid,
    port_id uuid,
    endpoint_role varchar(40),
    handoff varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    constraint uk_inv_service_endpoint_tenant_code unique (tenant_id, code)
);

create index ix_inv_service_endpoint_status on inv_service_endpoint (tenant_id, status);
create index ix_inv_service_endpoint_name on inv_service_endpoint (tenant_id, name);
create index ix_inv_service_endpoint_ext on inv_service_endpoint (external_ref);
create index ix_inv_service_endpoint_service_id on inv_service_endpoint (tenant_id, service_id);
create index ix_inv_service_endpoint_site_id on inv_service_endpoint (tenant_id, site_id);
create index ix_inv_service_endpoint_port_id on inv_service_endpoint (tenant_id, port_id);

comment on table inv_service_endpoint is 'Service Endpoint - Services';
