-- Rack Unit (Facility)
create table inv_rack_unit (
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
    rack_id uuid not null,
    unit_number integer not null,
    occupied boolean,
    mounted_device_id uuid,
    face varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    constraint uk_inv_rack_unit_tenant_code unique (tenant_id, code)
);

create index ix_inv_rack_unit_status on inv_rack_unit (tenant_id, status);
create index ix_inv_rack_unit_name on inv_rack_unit (tenant_id, name);
create index ix_inv_rack_unit_ext on inv_rack_unit (external_ref);
create index ix_inv_rack_unit_rack_id on inv_rack_unit (tenant_id, rack_id);
create index ix_inv_rack_unit_mounted_device_id on inv_rack_unit (tenant_id, mounted_device_id);

comment on table inv_rack_unit is 'Rack Unit - Facility';
