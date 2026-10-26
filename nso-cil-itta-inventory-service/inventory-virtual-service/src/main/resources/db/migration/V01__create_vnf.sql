-- Vnf (Virtual and Cloud)
create table inv_vnf (
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
    vnf_name varchar(255) not null,
    vnf_type varchar(255) not null,
    flavor varchar(255),
    vcpu integer,
    memory_gb integer,
    instance_count integer,
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    constraint uk_inv_vnf_tenant_code unique (tenant_id, code)
);

create index ix_inv_vnf_status on inv_vnf (tenant_id, status);
create index ix_inv_vnf_name on inv_vnf (tenant_id, name);
create index ix_inv_vnf_ext on inv_vnf (external_ref);


comment on table inv_vnf is 'Vnf - Virtual and Cloud';
