-- Transceiver (Port and Interface)
create table inv_transceiver (
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
    port_id uuid,
    form_factor varchar(40),
    wavelength_nm integer,
    reach_km numeric(18,4),
    serial_number varchar(255),
    vendor_part varchar(255),
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    constraint uk_inv_transceiver_tenant_code unique (tenant_id, code)
);

create index ix_inv_transceiver_status on inv_transceiver (tenant_id, status);
create index ix_inv_transceiver_name on inv_transceiver (tenant_id, name);
create index ix_inv_transceiver_ext on inv_transceiver (external_ref);
create index ix_inv_transceiver_port_id on inv_transceiver (tenant_id, port_id);

comment on table inv_transceiver is 'Transceiver - Port and Interface';
