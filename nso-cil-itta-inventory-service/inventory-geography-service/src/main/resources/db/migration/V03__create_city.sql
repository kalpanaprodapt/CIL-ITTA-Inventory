-- City (Geography)
create table inv_city (
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
    region_id uuid not null,
    postal_prefix varchar(255),
    population bigint,
    latitude numeric(18,4),
    longitude numeric(18,4),
    metro_area varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    constraint uk_inv_city_tenant_code unique (tenant_id, code)
);

create index ix_inv_city_status on inv_city (tenant_id, status);
create index ix_inv_city_name on inv_city (tenant_id, name);
create index ix_inv_city_ext on inv_city (external_ref);
create index ix_inv_city_region_id on inv_city (tenant_id, region_id);

comment on table inv_city is 'City - Geography';
