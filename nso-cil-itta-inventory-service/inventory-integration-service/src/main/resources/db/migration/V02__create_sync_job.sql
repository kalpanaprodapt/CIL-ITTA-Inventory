-- Sync Job (Integration Layer)
create table inv_sync_job (
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
    endpoint_id uuid not null,
    direction varchar(40),
    schedule_cron varchar(255),
    last_run_at timestamptz,
    last_status varchar(40),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    constraint uk_inv_sync_job_tenant_code unique (tenant_id, code)
);

create index ix_inv_sync_job_status on inv_sync_job (tenant_id, status);
create index ix_inv_sync_job_name on inv_sync_job (tenant_id, name);
create index ix_inv_sync_job_ext on inv_sync_job (external_ref);
create index ix_inv_sync_job_endpoint_id on inv_sync_job (tenant_id, endpoint_id);

comment on table inv_sync_job is 'Sync Job - Integration Layer';
