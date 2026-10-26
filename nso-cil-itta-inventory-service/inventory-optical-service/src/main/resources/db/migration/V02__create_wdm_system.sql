-- Wdm System (Optical)
create table inv_wdm_system (
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
    system_name varchar(255) not null,
    channel_capacity integer not null,
    band varchar(40),
    span_count integer,
    vendor_id uuid,
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    constraint uk_inv_wdm_system_tenant_code unique (tenant_id, code)
);

create index ix_inv_wdm_system_status on inv_wdm_system (tenant_id, status);
create index ix_inv_wdm_system_name on inv_wdm_system (tenant_id, name);
create index ix_inv_wdm_system_ext on inv_wdm_system (external_ref);
create index ix_inv_wdm_system_vendor_id on inv_wdm_system (tenant_id, vendor_id);

comment on table inv_wdm_system is 'Wdm System - Optical';
