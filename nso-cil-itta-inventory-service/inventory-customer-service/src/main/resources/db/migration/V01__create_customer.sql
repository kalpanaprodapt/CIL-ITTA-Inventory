-- Customer (Customers and SLA)
create table inv_customer (
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
    legal_name varchar(255) not null,
    segment varchar(40),
    tax_id varchar(255),
    industry varchar(255),
    account_manager varchar(255),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    constraint uk_inv_customer_tenant_code unique (tenant_id, code)
);

create index ix_inv_customer_status on inv_customer (tenant_id, status);
create index ix_inv_customer_name on inv_customer (tenant_id, name);
create index ix_inv_customer_ext on inv_customer (external_ref);


comment on table inv_customer is 'Customer - Customers and SLA';
