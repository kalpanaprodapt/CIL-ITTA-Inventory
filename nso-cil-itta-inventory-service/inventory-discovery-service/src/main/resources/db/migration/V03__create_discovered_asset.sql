-- Discovered Asset (Discovery and Reconciliation)
create table inv_discovered_asset (
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
    asset_type varchar(255) not null,
    fingerprint varchar(255) not null,
    raw_payload varchar(4000),
    matched_asset_id uuid,
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    constraint uk_inv_discovered_asset_tenant_code unique (tenant_id, code)
);

create index ix_inv_discovered_asset_status on inv_discovered_asset (tenant_id, status);
create index ix_inv_discovered_asset_name on inv_discovered_asset (tenant_id, name);
create index ix_inv_discovered_asset_ext on inv_discovered_asset (external_ref);
create index ix_inv_discovered_asset_job_id on inv_discovered_asset (tenant_id, job_id);
create index ix_inv_discovered_asset_matched_asset_id on inv_discovered_asset (tenant_id, matched_asset_id);

comment on table inv_discovered_asset is 'Discovered Asset - Discovery and Reconciliation';
