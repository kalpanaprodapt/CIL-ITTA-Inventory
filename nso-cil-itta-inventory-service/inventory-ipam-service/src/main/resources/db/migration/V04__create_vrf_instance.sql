-- Vrf Instance (IP Management)
create table inv_vrf_instance (
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
    vrf_name varchar(255) not null,
    route_distinguisher varchar(255) not null,
    import_targets varchar(255),
    export_targets varchar(255),
    device_id uuid,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    constraint uk_inv_vrf_instance_tenant_code unique (tenant_id, code)
);

create index ix_inv_vrf_instance_status on inv_vrf_instance (tenant_id, status);
create index ix_inv_vrf_instance_name on inv_vrf_instance (tenant_id, name);
create index ix_inv_vrf_instance_ext on inv_vrf_instance (external_ref);
create index ix_inv_vrf_instance_device_id on inv_vrf_instance (tenant_id, device_id);

comment on table inv_vrf_instance is 'Vrf Instance - IP Management';
