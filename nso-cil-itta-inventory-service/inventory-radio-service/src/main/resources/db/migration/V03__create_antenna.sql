-- Antenna (Radio and Mobile)
create table inv_antenna (
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
    sector_id uuid not null,
    model varchar(255),
    height_m numeric(18,4),
    azimuth_deg numeric(18,4),
    tilt_deg numeric(18,4),
    gain_dbi numeric(18,4),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    constraint uk_inv_antenna_tenant_code unique (tenant_id, code)
);

create index ix_inv_antenna_status on inv_antenna (tenant_id, status);
create index ix_inv_antenna_name on inv_antenna (tenant_id, name);
create index ix_inv_antenna_ext on inv_antenna (external_ref);
create index ix_inv_antenna_sector_id on inv_antenna (tenant_id, sector_id);

comment on table inv_antenna is 'Antenna - Radio and Mobile';
