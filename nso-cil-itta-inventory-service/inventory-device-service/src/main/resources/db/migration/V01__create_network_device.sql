-- Network Device (Network Device)
create table inv_network_device (
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
    hostname varchar(255) not null,
    device_role varchar(40),
    mgmt_ip varchar(255),
    serial_number varchar(255),
    model_id uuid,
    rack_unit_id uuid,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    constraint uk_inv_network_device_tenant_code unique (tenant_id, code)
);

create index ix_inv_network_device_status on inv_network_device (tenant_id, status);
create index ix_inv_network_device_name on inv_network_device (tenant_id, name);
create index ix_inv_network_device_ext on inv_network_device (external_ref);
create index ix_inv_network_device_model_id on inv_network_device (tenant_id, model_id);
create index ix_inv_network_device_rack_unit_id on inv_network_device (tenant_id, rack_unit_id);

comment on table inv_network_device is 'Network Device - Network Device';
