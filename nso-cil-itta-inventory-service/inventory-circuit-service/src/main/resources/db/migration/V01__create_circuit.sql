-- Circuit (Circuits)
create table inv_circuit (
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
    circuit_id varchar(255) not null,
    circuit_type varchar(40),
    bandwidth_mbps bigint not null,
    provider_id uuid,
    a_site_id uuid,
    z_site_id uuid,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    constraint uk_inv_circuit_tenant_code unique (tenant_id, code)
);

create index ix_inv_circuit_status on inv_circuit (tenant_id, status);
create index ix_inv_circuit_name on inv_circuit (tenant_id, name);
create index ix_inv_circuit_ext on inv_circuit (external_ref);
create index ix_inv_circuit_provider_id on inv_circuit (tenant_id, provider_id);
create index ix_inv_circuit_a_site_id on inv_circuit (tenant_id, a_site_id);
create index ix_inv_circuit_z_site_id on inv_circuit (tenant_id, z_site_id);

comment on table inv_circuit is 'Circuit - Circuits';
