-- Route Target (L2 and L3 Routing)
create table inv_route_target (
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
    asn bigint not null,
    assigned_number bigint not null,
    rt_type varchar(40),
    vrf_id uuid,
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    constraint uk_inv_route_target_tenant_code unique (tenant_id, code)
);

create index ix_inv_route_target_status on inv_route_target (tenant_id, status);
create index ix_inv_route_target_name on inv_route_target (tenant_id, name);
create index ix_inv_route_target_ext on inv_route_target (external_ref);
create index ix_inv_route_target_vrf_id on inv_route_target (tenant_id, vrf_id);

comment on table inv_route_target is 'Route Target - L2 and L3 Routing';
