-- Vlan (L2 and L3 Routing)
create table inv_vlan (
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
    vlan_id integer not null,
    vlan_name varchar(255) not null,
    vlan_type varchar(40),
    pool_id uuid,
    stp_mode varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    constraint uk_inv_vlan_tenant_code unique (tenant_id, code)
);

create index ix_inv_vlan_status on inv_vlan (tenant_id, status);
create index ix_inv_vlan_name on inv_vlan (tenant_id, name);
create index ix_inv_vlan_ext on inv_vlan (external_ref);
create index ix_inv_vlan_pool_id on inv_vlan (tenant_id, pool_id);

comment on table inv_vlan is 'Vlan - L2 and L3 Routing';
