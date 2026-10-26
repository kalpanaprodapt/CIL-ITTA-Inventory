-- Vpn Service (MPLS and VPN)
create table inv_vpn_service (
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
    vpn_name varchar(255) not null,
    vpn_type varchar(40),
    vrf_id uuid,
    customer_id uuid,
    topology varchar(40),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    constraint uk_inv_vpn_service_tenant_code unique (tenant_id, code)
);

create index ix_inv_vpn_service_status on inv_vpn_service (tenant_id, status);
create index ix_inv_vpn_service_name on inv_vpn_service (tenant_id, name);
create index ix_inv_vpn_service_ext on inv_vpn_service (external_ref);
create index ix_inv_vpn_service_vrf_id on inv_vpn_service (tenant_id, vrf_id);
create index ix_inv_vpn_service_customer_id on inv_vpn_service (tenant_id, customer_id);

comment on table inv_vpn_service is 'Vpn Service - MPLS and VPN';
