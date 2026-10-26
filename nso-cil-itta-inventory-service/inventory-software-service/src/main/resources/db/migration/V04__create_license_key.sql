-- License Key (Software and Licensing)
create table inv_license_key (
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
    pool_id uuid not null,
    key_value varchar(255) not null,
    feature varchar(255) not null,
    expires_on date,
    assigned_device_id uuid,
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    constraint uk_inv_license_key_tenant_code unique (tenant_id, code)
);

create index ix_inv_license_key_status on inv_license_key (tenant_id, status);
create index ix_inv_license_key_name on inv_license_key (tenant_id, name);
create index ix_inv_license_key_ext on inv_license_key (external_ref);
create index ix_inv_license_key_pool_id on inv_license_key (tenant_id, pool_id);
create index ix_inv_license_key_assigned_device_id on inv_license_key (tenant_id, assigned_device_id);

comment on table inv_license_key is 'License Key - Software and Licensing';
