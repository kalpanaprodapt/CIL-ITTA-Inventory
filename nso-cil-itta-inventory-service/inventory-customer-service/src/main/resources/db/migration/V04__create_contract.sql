-- Contract (Customers and SLA)
create table inv_contract (
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
    contract_number varchar(255) not null,
    start_date date not null,
    end_date date,
    term_months integer,
    auto_renew boolean,
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    constraint uk_inv_contract_tenant_code unique (tenant_id, code)
);

create index ix_inv_contract_status on inv_contract (tenant_id, status);
create index ix_inv_contract_name on inv_contract (tenant_id, name);
create index ix_inv_contract_ext on inv_contract (external_ref);
create index ix_inv_contract_customer_id on inv_contract (tenant_id, customer_id);

comment on table inv_contract is 'Contract - Customers and SLA';
