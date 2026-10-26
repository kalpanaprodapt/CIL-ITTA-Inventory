-- Lag Group (Port and Interface)
create table inv_lag_group (
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
    device_id uuid not null,
    lag_name varchar(255) not null,
    member_count integer,
    lacp_mode varchar(40),
    min_links integer,
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    constraint uk_inv_lag_group_tenant_code unique (tenant_id, code)
);

create index ix_inv_lag_group_status on inv_lag_group (tenant_id, status);
create index ix_inv_lag_group_name on inv_lag_group (tenant_id, name);
create index ix_inv_lag_group_ext on inv_lag_group (external_ref);
create index ix_inv_lag_group_device_id on inv_lag_group (tenant_id, device_id);

comment on table inv_lag_group is 'Lag Group - Port and Interface';
