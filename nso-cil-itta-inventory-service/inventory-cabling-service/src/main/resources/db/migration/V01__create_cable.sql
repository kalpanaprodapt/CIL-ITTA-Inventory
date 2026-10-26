-- Cable (Cabling)
create table inv_cable (
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
    cable_type varchar(40),
    length_m numeric(18,4),
    a_end_port_id uuid,
    z_end_port_id uuid,
    color varchar(255),
    label varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    constraint uk_inv_cable_tenant_code unique (tenant_id, code)
);

create index ix_inv_cable_status on inv_cable (tenant_id, status);
create index ix_inv_cable_name on inv_cable (tenant_id, name);
create index ix_inv_cable_ext on inv_cable (external_ref);
create index ix_inv_cable_a_end_port_id on inv_cable (tenant_id, a_end_port_id);
create index ix_inv_cable_z_end_port_id on inv_cable (tenant_id, z_end_port_id);

comment on table inv_cable is 'Cable - Cabling';
