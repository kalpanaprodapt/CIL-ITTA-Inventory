-- Backhaul Link (Radio and Mobile)
create table inv_backhaul_link (
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
    base_station_id uuid not null,
    link_medium varchar(40),
    capacity_mbps bigint not null,
    latency_ms numeric(18,4),
    circuit_ref varchar(255),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    constraint uk_inv_backhaul_link_tenant_code unique (tenant_id, code)
);

create index ix_inv_backhaul_link_status on inv_backhaul_link (tenant_id, status);
create index ix_inv_backhaul_link_name on inv_backhaul_link (tenant_id, name);
create index ix_inv_backhaul_link_ext on inv_backhaul_link (external_ref);
create index ix_inv_backhaul_link_base_station_id on inv_backhaul_link (tenant_id, base_station_id);

comment on table inv_backhaul_link is 'Backhaul Link - Radio and Mobile';
