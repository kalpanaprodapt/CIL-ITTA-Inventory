-- Sla Profile (Customers and SLA)
create table inv_sla_profile (
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
    profile_name varchar(255) not null,
    availability_pct numeric(18,4) not null,
    mttr_minutes integer,
    latency_ms numeric(18,4),
    jitter_ms numeric(18,4),
    credit_policy varchar(4000),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    constraint uk_inv_sla_profile_tenant_code unique (tenant_id, code)
);

create index ix_inv_sla_profile_status on inv_sla_profile (tenant_id, status);
create index ix_inv_sla_profile_name on inv_sla_profile (tenant_id, name);
create index ix_inv_sla_profile_ext on inv_sla_profile (external_ref);


comment on table inv_sla_profile is 'Sla Profile - Customers and SLA';
