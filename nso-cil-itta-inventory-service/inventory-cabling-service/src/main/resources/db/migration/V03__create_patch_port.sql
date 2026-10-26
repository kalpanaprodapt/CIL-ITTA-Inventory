-- Patch Port (Cabling)
create table inv_patch_port (
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
    panel_id uuid not null,
    port_number integer not null,
    connected_cable_id uuid,
    polarity varchar(255),
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    constraint uk_inv_patch_port_tenant_code unique (tenant_id, code)
);

create index ix_inv_patch_port_status on inv_patch_port (tenant_id, status);
create index ix_inv_patch_port_name on inv_patch_port (tenant_id, name);
create index ix_inv_patch_port_ext on inv_patch_port (external_ref);
create index ix_inv_patch_port_panel_id on inv_patch_port (tenant_id, panel_id);
create index ix_inv_patch_port_connected_cable_id on inv_patch_port (tenant_id, connected_cable_id);

comment on table inv_patch_port is 'Patch Port - Cabling';
