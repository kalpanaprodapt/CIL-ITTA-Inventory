-- Maintenance Window (Change and Work Management)
create table inv_maintenance_window (
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
    window_name varchar(255) not null,
    starts_at timestamptz not null,
    ends_at timestamptz not null,
    scope varchar(255),
    impact_level varchar(40),
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    constraint uk_inv_maintenance_window_tenant_code unique (tenant_id, code)
);

create index ix_inv_maintenance_window_status on inv_maintenance_window (tenant_id, status);
create index ix_inv_maintenance_window_name on inv_maintenance_window (tenant_id, name);
create index ix_inv_maintenance_window_ext on inv_maintenance_window (external_ref);


comment on table inv_maintenance_window is 'Maintenance Window - Change and Work Management';
