-- Optical Channel (Optical)
create table inv_optical_channel (
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
    wavelength_nm numeric(18,4) not null,
    channel_number integer not null,
    grid_type varchar(40),
    bitrate_gbps numeric(18,4),
    system_id uuid,
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    constraint uk_inv_optical_channel_tenant_code unique (tenant_id, code)
);

create index ix_inv_optical_channel_status on inv_optical_channel (tenant_id, status);
create index ix_inv_optical_channel_name on inv_optical_channel (tenant_id, name);
create index ix_inv_optical_channel_ext on inv_optical_channel (external_ref);
create index ix_inv_optical_channel_system_id on inv_optical_channel (tenant_id, system_id);

comment on table inv_optical_channel is 'Optical Channel - Optical';
