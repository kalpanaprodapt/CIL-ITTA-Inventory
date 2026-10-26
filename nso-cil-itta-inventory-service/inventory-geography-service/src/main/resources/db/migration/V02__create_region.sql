-- Region (Geography)
create table inv_region (
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
    country_id uuid not null,
    region_type varchar(40),
    population bigint,
    latitude numeric(18,4),
    longitude numeric(18,4),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    constraint uk_inv_region_tenant_code unique (tenant_id, code)
);

create index ix_inv_region_status on inv_region (tenant_id, status);
create index ix_inv_region_name on inv_region (tenant_id, name);
create index ix_inv_region_ext on inv_region (external_ref);
create index ix_inv_region_country_id on inv_region (tenant_id, country_id);

comment on table inv_region is 'Region - Geography';
