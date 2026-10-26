-- Depreciation Schedule (Asset and Finance)
create table inv_depreciation_schedule (
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
    asset_id uuid not null,
    method varchar(40),
    useful_life_months integer not null,
    salvage_value numeric(18,4),
    start_date date,
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    constraint uk_inv_depreciation_schedule_tenant_code unique (tenant_id, code)
);

create index ix_inv_depreciation_schedule_status on inv_depreciation_schedule (tenant_id, status);
create index ix_inv_depreciation_schedule_name on inv_depreciation_schedule (tenant_id, name);
create index ix_inv_depreciation_schedule_ext on inv_depreciation_schedule (external_ref);
create index ix_inv_depreciation_schedule_asset_id on inv_depreciation_schedule (tenant_id, asset_id);

comment on table inv_depreciation_schedule is 'Depreciation Schedule - Asset and Finance';
