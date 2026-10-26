-- Vlan Pool (L2 and L3 Routing)
create table inv_vlan_pool (
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
    pool_name varchar(255) not null,
    range_start integer not null,
    range_end integer not null,
    allocated integer,
    scope varchar(255),
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    constraint uk_inv_vlan_pool_tenant_code unique (tenant_id, code)
);

create index ix_inv_vlan_pool_status on inv_vlan_pool (tenant_id, status);
create index ix_inv_vlan_pool_name on inv_vlan_pool (tenant_id, name);
create index ix_inv_vlan_pool_ext on inv_vlan_pool (external_ref);


comment on table inv_vlan_pool is 'Vlan Pool - L2 and L3 Routing';
