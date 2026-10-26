-- Cloud Account (Virtual and Cloud)
create table inv_cloud_account (
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
    provider varchar(40),
    account_ref varchar(255) not null,
    subscription_name varchar(255),
    monthly_budget numeric(18,4),
    environment varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    constraint uk_inv_cloud_account_tenant_code unique (tenant_id, code)
);

create index ix_inv_cloud_account_status on inv_cloud_account (tenant_id, status);
create index ix_inv_cloud_account_name on inv_cloud_account (tenant_id, name);
create index ix_inv_cloud_account_ext on inv_cloud_account (external_ref);


comment on table inv_cloud_account is 'Cloud Account - Virtual and Cloud';
