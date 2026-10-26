-- Chassis (Network Device)
create table inv_chassis (
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
    device_id uuid not null,
    slot_count integer not null,
    serial_number varchar(255),
    backplane_gbps numeric(18,4),
    redundancy_mode varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    constraint uk_inv_chassis_tenant_code unique (tenant_id, code)
);

create index ix_inv_chassis_status on inv_chassis (tenant_id, status);
create index ix_inv_chassis_name on inv_chassis (tenant_id, name);
create index ix_inv_chassis_ext on inv_chassis (external_ref);
create index ix_inv_chassis_device_id on inv_chassis (tenant_id, device_id);

comment on table inv_chassis is 'Chassis - Network Device';
