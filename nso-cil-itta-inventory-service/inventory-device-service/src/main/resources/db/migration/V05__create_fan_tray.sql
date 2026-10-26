-- Fan Tray (Network Device)
create table inv_fan_tray (
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
    chassis_id uuid not null,
    slot_number integer,
    fan_count integer,
    airflow varchar(40),
    serial_number varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    constraint uk_inv_fan_tray_tenant_code unique (tenant_id, code)
);

create index ix_inv_fan_tray_status on inv_fan_tray (tenant_id, status);
create index ix_inv_fan_tray_name on inv_fan_tray (tenant_id, name);
create index ix_inv_fan_tray_ext on inv_fan_tray (external_ref);
create index ix_inv_fan_tray_chassis_id on inv_fan_tray (tenant_id, chassis_id);

comment on table inv_fan_tray is 'Fan Tray - Network Device';
