-- Ip Pool (IP Management)
create table inv_ip_pool (
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
    subnet_id uuid not null,
    range_start varchar(255) not null,
    range_end varchar(255) not null,
    pool_type varchar(40),
    lease_seconds integer,
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    constraint uk_inv_ip_pool_tenant_code unique (tenant_id, code)
);

create index ix_inv_ip_pool_status on inv_ip_pool (tenant_id, status);
create index ix_inv_ip_pool_name on inv_ip_pool (tenant_id, name);
create index ix_inv_ip_pool_ext on inv_ip_pool (external_ref);
create index ix_inv_ip_pool_subnet_id on inv_ip_pool (tenant_id, subnet_id);

comment on table inv_ip_pool is 'Ip Pool - IP Management';
