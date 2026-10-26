-- Base Station (Radio and Mobile)
create table inv_base_station (
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
    enb_id bigint not null,
    technology varchar(40),
    sector_count integer,
    cell_count integer,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    constraint uk_inv_base_station_tenant_code unique (tenant_id, code)
);

create index ix_inv_base_station_status on inv_base_station (tenant_id, status);
create index ix_inv_base_station_name on inv_base_station (tenant_id, name);
create index ix_inv_base_station_ext on inv_base_station (external_ref);
create index ix_inv_base_station_site_id on inv_base_station (tenant_id, site_id);

comment on table inv_base_station is 'Base Station - Radio and Mobile';
