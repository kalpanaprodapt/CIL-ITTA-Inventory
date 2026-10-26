-- Hardware Model (Vendor and Model)
create table inv_hardware_model (
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
    vendor_id uuid not null,
    model_number varchar(255) not null,
    form_factor varchar(255),
    power_watts integer,
    eol_date date,
    eos_date date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    constraint uk_inv_hardware_model_tenant_code unique (tenant_id, code)
);

create index ix_inv_hardware_model_status on inv_hardware_model (tenant_id, status);
create index ix_inv_hardware_model_name on inv_hardware_model (tenant_id, name);
create index ix_inv_hardware_model_ext on inv_hardware_model (external_ref);
create index ix_inv_hardware_model_vendor_id on inv_hardware_model (tenant_id, vendor_id);

comment on table inv_hardware_model is 'Hardware Model - Vendor and Model';
