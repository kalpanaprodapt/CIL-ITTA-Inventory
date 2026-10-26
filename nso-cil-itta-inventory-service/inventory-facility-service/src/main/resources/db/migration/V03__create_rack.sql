-- Rack (Facility)
create table inv_rack (
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
    rack_units integer not null,
    width_mm integer,
    depth_mm integer,
    max_weight_kg numeric(18,4),
    row_label varchar(255),
    position varchar(255),
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    constraint uk_inv_rack_tenant_code unique (tenant_id, code)
);

create index ix_inv_rack_status on inv_rack (tenant_id, status);
create index ix_inv_rack_name on inv_rack (tenant_id, name);
create index ix_inv_rack_ext on inv_rack (external_ref);
create index ix_inv_rack_room_id on inv_rack (tenant_id, room_id);

comment on table inv_rack is 'Rack - Facility';
