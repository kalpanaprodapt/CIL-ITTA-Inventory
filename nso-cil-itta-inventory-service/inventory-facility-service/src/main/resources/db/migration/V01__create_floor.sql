-- Floor (Facility)
create table inv_floor (
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
    building_id uuid not null,
    level integer,
    area_sqm numeric(18,4),
    load_capacity_kg numeric(18,4),
    has_raised_floor boolean,
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    constraint uk_inv_floor_tenant_code unique (tenant_id, code)
);

create index ix_inv_floor_status on inv_floor (tenant_id, status);
create index ix_inv_floor_name on inv_floor (tenant_id, name);
create index ix_inv_floor_ext on inv_floor (external_ref);
create index ix_inv_floor_building_id on inv_floor (tenant_id, building_id);

comment on table inv_floor is 'Floor - Facility';
