-- Integration Endpoint (Integration Layer)
create table inv_integration_endpoint (
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
    endpoint_name varchar(255) not null,
    target_system varchar(255) not null,
    base_url varchar(255) not null,
    auth_type varchar(40),
    timeout_ms integer,
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    constraint uk_inv_integration_endpoint_tenant_code unique (tenant_id, code)
);

create index ix_inv_integration_endpoint_status on inv_integration_endpoint (tenant_id, status);
create index ix_inv_integration_endpoint_name on inv_integration_endpoint (tenant_id, name);
create index ix_inv_integration_endpoint_ext on inv_integration_endpoint (external_ref);


comment on table inv_integration_endpoint is 'Integration Endpoint - Integration Layer';
