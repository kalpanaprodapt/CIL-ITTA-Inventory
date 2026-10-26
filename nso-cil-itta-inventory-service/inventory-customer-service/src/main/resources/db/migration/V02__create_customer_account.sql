-- Customer Account (Customers and SLA)
create table inv_customer_account (
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
    customer_id uuid not null,
    account_number varchar(255) not null,
    billing_cycle varchar(40),
    currency varchar(255),
    credit_limit numeric(18,4),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    constraint uk_inv_customer_account_tenant_code unique (tenant_id, code)
);

create index ix_inv_customer_account_status on inv_customer_account (tenant_id, status);
create index ix_inv_customer_account_name on inv_customer_account (tenant_id, name);
create index ix_inv_customer_account_ext on inv_customer_account (external_ref);
create index ix_inv_customer_account_customer_id on inv_customer_account (tenant_id, customer_id);

comment on table inv_customer_account is 'Customer Account - Customers and SLA';
