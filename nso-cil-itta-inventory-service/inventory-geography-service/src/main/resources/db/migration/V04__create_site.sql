-- Site (Geography)
create table inv_site (
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
    city_id uuid not null,
    site_type varchar(40),
    address varchar(255),
    latitude numeric(18,4),
    longitude numeric(18,4),
    power_kw numeric(18,4),
    access_hours varchar(255),
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    constraint uk_inv_site_tenant_code unique (tenant_id, code)
);

create index ix_inv_site_status on inv_site (tenant_id, status);
create index ix_inv_site_name on inv_site (tenant_id, name);
create index ix_inv_site_ext on inv_site (external_ref);
create index ix_inv_site_city_id on inv_site (tenant_id, city_id);

comment on table inv_site is 'Site - Geography';
