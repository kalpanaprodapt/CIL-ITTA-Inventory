-- License Pool (Software and Licensing)
create table inv_license_pool (
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
    vendor_id uuid not null,
    pool_name varchar(255) not null,
    total_seats integer not null,
    used_seats integer,
    renewal_date date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    constraint uk_inv_license_pool_tenant_code unique (tenant_id, code)
);

create index ix_inv_license_pool_status on inv_license_pool (tenant_id, status);
create index ix_inv_license_pool_name on inv_license_pool (tenant_id, name);
create index ix_inv_license_pool_ext on inv_license_pool (external_ref);
create index ix_inv_license_pool_vendor_id on inv_license_pool (tenant_id, vendor_id);

comment on table inv_license_pool is 'License Pool - Software and Licensing';
