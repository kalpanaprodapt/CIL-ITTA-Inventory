-- Support Contract (Vendor and Model)
create table inv_support_contract (
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
    vendor_id uuid not null,
    contract_number varchar(255) not null,
    start_date date not null,
    end_date date not null,
    coverage_level varchar(40),
    annual_cost numeric(18,4),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    constraint uk_inv_support_contract_tenant_code unique (tenant_id, code)
);

create index ix_inv_support_contract_status on inv_support_contract (tenant_id, status);
create index ix_inv_support_contract_name on inv_support_contract (tenant_id, name);
create index ix_inv_support_contract_ext on inv_support_contract (external_ref);
create index ix_inv_support_contract_vendor_id on inv_support_contract (tenant_id, vendor_id);

comment on table inv_support_contract is 'Support Contract - Vendor and Model';
