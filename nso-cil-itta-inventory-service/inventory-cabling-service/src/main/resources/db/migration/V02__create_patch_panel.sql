-- Patch Panel (Cabling)
create table inv_patch_panel (
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
    room_id uuid not null,
    rack_id uuid,
    panel_type varchar(40),
    port_count integer not null,
    label varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    constraint uk_inv_patch_panel_tenant_code unique (tenant_id, code)
);

create index ix_inv_patch_panel_status on inv_patch_panel (tenant_id, status);
create index ix_inv_patch_panel_name on inv_patch_panel (tenant_id, name);
create index ix_inv_patch_panel_ext on inv_patch_panel (external_ref);
create index ix_inv_patch_panel_room_id on inv_patch_panel (tenant_id, room_id);
create index ix_inv_patch_panel_rack_id on inv_patch_panel (tenant_id, rack_id);

comment on table inv_patch_panel is 'Patch Panel - Cabling';
