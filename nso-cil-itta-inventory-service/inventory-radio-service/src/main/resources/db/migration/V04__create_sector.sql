-- Sector (Radio and Mobile)
create table inv_sector (
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
    base_station_id uuid not null,
    sector_number integer not null,
    pci integer,
    frequency_mhz integer,
    bandwidth_mhz integer,
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    constraint uk_inv_sector_tenant_code unique (tenant_id, code)
);

create index ix_inv_sector_status on inv_sector (tenant_id, status);
create index ix_inv_sector_name on inv_sector (tenant_id, name);
create index ix_inv_sector_ext on inv_sector (external_ref);
create index ix_inv_sector_base_station_id on inv_sector (tenant_id, base_station_id);

comment on table inv_sector is 'Sector - Radio and Mobile';
