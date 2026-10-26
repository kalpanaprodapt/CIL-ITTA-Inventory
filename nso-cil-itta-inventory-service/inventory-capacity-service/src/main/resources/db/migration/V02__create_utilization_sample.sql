-- Utilization Sample (Capacity Planning)
create table inv_utilization_sample (
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
    target_ref varchar(255) not null,
    metric varchar(255) not null,
    sampled_at timestamptz not null,
    value_pct numeric(18,4) not null,
    peak boolean,
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    constraint uk_inv_utilization_sample_tenant_code unique (tenant_id, code)
);

create index ix_inv_utilization_sample_status on inv_utilization_sample (tenant_id, status);
create index ix_inv_utilization_sample_name on inv_utilization_sample (tenant_id, name);
create index ix_inv_utilization_sample_ext on inv_utilization_sample (external_ref);


comment on table inv_utilization_sample is 'Utilization Sample - Capacity Planning';
