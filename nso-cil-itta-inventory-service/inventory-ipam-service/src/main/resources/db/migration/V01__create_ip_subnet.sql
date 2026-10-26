-- Ip Subnet (IP Management)
create table inv_ip_subnet (
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
    cidr varchar(255) not null,
    ip_version varchar(40),
    vrf_id uuid,
    gateway varchar(255),
    vlan_id uuid,
    utilization_pct numeric(18,4),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    constraint uk_inv_ip_subnet_tenant_code unique (tenant_id, code)
);

create index ix_inv_ip_subnet_status on inv_ip_subnet (tenant_id, status);
create index ix_inv_ip_subnet_name on inv_ip_subnet (tenant_id, name);
create index ix_inv_ip_subnet_ext on inv_ip_subnet (external_ref);
create index ix_inv_ip_subnet_vrf_id on inv_ip_subnet (tenant_id, vrf_id);
create index ix_inv_ip_subnet_vlan_id on inv_ip_subnet (tenant_id, vlan_id);

comment on table inv_ip_subnet is 'Ip Subnet - IP Management';
