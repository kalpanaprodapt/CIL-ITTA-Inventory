-- Hypervisor (Virtual and Cloud)
create table inv_hypervisor (
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
    host_device_id uuid not null,
    hypervisor_type varchar(40),
    hv_version varchar(255),
    vcpu_capacity integer,
    memory_gb_capacity integer,
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    constraint uk_inv_hypervisor_tenant_code unique (tenant_id, code)
);

create index ix_inv_hypervisor_status on inv_hypervisor (tenant_id, status);
create index ix_inv_hypervisor_name on inv_hypervisor (tenant_id, name);
create index ix_inv_hypervisor_ext on inv_hypervisor (external_ref);
create index ix_inv_hypervisor_host_device_id on inv_hypervisor (tenant_id, host_device_id);

comment on table inv_hypervisor is 'Hypervisor - Virtual and Cloud';
