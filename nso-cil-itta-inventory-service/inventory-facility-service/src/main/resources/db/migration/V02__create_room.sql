-- Room (Facility)
create table inv_room (
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
    floor_id uuid not null,
    room_type varchar(40),
    area_sqm numeric(18,4),
    cooling_tons numeric(18,4),
    fire_zone varchar(255),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    constraint uk_inv_room_tenant_code unique (tenant_id, code)
);

create index ix_inv_room_status on inv_room (tenant_id, status);
create index ix_inv_room_name on inv_room (tenant_id, name);
create index ix_inv_room_ext on inv_room (external_ref);
create index ix_inv_room_floor_id on inv_room (tenant_id, floor_id);

comment on table inv_room is 'Room - Facility';
