-- Power Supply (Network Device)
create table inv_power_supply (
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
    wattage integer,
    feed_id uuid,
    serial_number varchar(255),
    efficiency numeric(18,4),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    constraint uk_inv_power_supply_tenant_code unique (tenant_id, code)
);

create index ix_inv_power_supply_status on inv_power_supply (tenant_id, status);
create index ix_inv_power_supply_name on inv_power_supply (tenant_id, name);
create index ix_inv_power_supply_ext on inv_power_supply (external_ref);
create index ix_inv_power_supply_chassis_id on inv_power_supply (tenant_id, chassis_id);
create index ix_inv_power_supply_feed_id on inv_power_supply (tenant_id, feed_id);

comment on table inv_power_supply is 'Power Supply - Network Device';
