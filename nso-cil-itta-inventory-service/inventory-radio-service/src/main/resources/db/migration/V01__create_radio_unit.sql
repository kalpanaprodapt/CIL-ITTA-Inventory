-- Radio Unit (Radio and Mobile)
create table inv_radio_unit (
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
    unit_type varchar(40),
    band_mhz integer,
    tx_power_dbm numeric(18,4),
    serial_number varchar(255),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    constraint uk_inv_radio_unit_tenant_code unique (tenant_id, code)
);

create index ix_inv_radio_unit_status on inv_radio_unit (tenant_id, status);
create index ix_inv_radio_unit_name on inv_radio_unit (tenant_id, name);
create index ix_inv_radio_unit_ext on inv_radio_unit (external_ref);
create index ix_inv_radio_unit_site_id on inv_radio_unit (tenant_id, site_id);

comment on table inv_radio_unit is 'Radio Unit - Radio and Mobile';
