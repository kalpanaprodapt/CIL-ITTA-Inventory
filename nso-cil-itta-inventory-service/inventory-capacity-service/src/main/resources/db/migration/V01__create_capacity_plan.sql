-- Capacity Plan (Capacity Planning)
create table inv_capacity_plan (
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
    plan_name varchar(255) not null,
    horizon_months integer not null,
    target_utilization_pct numeric(18,4),
    owner varchar(255),
    approved_on date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    constraint uk_inv_capacity_plan_tenant_code unique (tenant_id, code)
);

create index ix_inv_capacity_plan_status on inv_capacity_plan (tenant_id, status);
create index ix_inv_capacity_plan_name on inv_capacity_plan (tenant_id, name);
create index ix_inv_capacity_plan_ext on inv_capacity_plan (external_ref);


comment on table inv_capacity_plan is 'Capacity Plan - Capacity Planning';
