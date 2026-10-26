-- Building (Geography)
create table inv_building (
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
    site_id uuid not null,
    floors integer,
    year_built integer,
    ownership varchar(40),
    lease_expiry date,
    security_level integer,
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    constraint uk_inv_building_tenant_code unique (tenant_id, code)
);

create index ix_inv_building_status on inv_building (tenant_id, status);
create index ix_inv_building_name on inv_building (tenant_id, name);
create index ix_inv_building_ext on inv_building (external_ref);
create index ix_inv_building_site_id on inv_building (tenant_id, site_id);

comment on table inv_building is 'Building - Geography';
