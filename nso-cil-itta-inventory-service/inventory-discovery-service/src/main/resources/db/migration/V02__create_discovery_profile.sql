-- Discovery Profile (Discovery and Reconciliation)
create table inv_discovery_profile (
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
    protocol varchar(40),
    credential_ref varchar(255),
    scope_cidr varchar(255),
    schedule_cron varchar(255),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    constraint uk_inv_discovery_profile_tenant_code unique (tenant_id, code)
);

create index ix_inv_discovery_profile_status on inv_discovery_profile (tenant_id, status);
create index ix_inv_discovery_profile_name on inv_discovery_profile (tenant_id, name);
create index ix_inv_discovery_profile_ext on inv_discovery_profile (external_ref);


comment on table inv_discovery_profile is 'Discovery Profile - Discovery and Reconciliation';
