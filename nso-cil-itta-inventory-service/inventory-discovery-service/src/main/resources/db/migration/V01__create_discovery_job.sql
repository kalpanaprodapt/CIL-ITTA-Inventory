-- Discovery Job (Discovery and Reconciliation)
create table inv_discovery_job (
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
    profile_id uuid not null,
    started_at timestamptz,
    finished_at timestamptz,
    job_state varchar(40),
    assets_found integer,
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    constraint uk_inv_discovery_job_tenant_code unique (tenant_id, code)
);

create index ix_inv_discovery_job_status on inv_discovery_job (tenant_id, status);
create index ix_inv_discovery_job_name on inv_discovery_job (tenant_id, name);
create index ix_inv_discovery_job_ext on inv_discovery_job (external_ref);
create index ix_inv_discovery_job_profile_id on inv_discovery_job (tenant_id, profile_id);

comment on table inv_discovery_job is 'Discovery Job - Discovery and Reconciliation';
