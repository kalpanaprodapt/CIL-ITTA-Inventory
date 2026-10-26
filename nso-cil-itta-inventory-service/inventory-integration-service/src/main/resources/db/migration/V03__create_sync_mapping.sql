-- Sync Mapping (Integration Layer)
create table inv_sync_mapping (
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
    job_id uuid not null,
    source_field varchar(255) not null,
    target_field varchar(255) not null,
    transform varchar(255),
    required boolean,
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    constraint uk_inv_sync_mapping_tenant_code unique (tenant_id, code)
);

create index ix_inv_sync_mapping_status on inv_sync_mapping (tenant_id, status);
create index ix_inv_sync_mapping_name on inv_sync_mapping (tenant_id, name);
create index ix_inv_sync_mapping_ext on inv_sync_mapping (external_ref);
create index ix_inv_sync_mapping_job_id on inv_sync_mapping (tenant_id, job_id);

comment on table inv_sync_mapping is 'Sync Mapping - Integration Layer';
