-- Compliance Check (Compliance and Audit)
create table inv_compliance_check (
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
    rule_id uuid not null,
    target_ref varchar(255) not null,
    checked_at timestamptz not null,
    result varchar(40),
    evidence varchar(4000),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    constraint uk_inv_compliance_check_tenant_code unique (tenant_id, code)
);

create index ix_inv_compliance_check_status on inv_compliance_check (tenant_id, status);
create index ix_inv_compliance_check_name on inv_compliance_check (tenant_id, name);
create index ix_inv_compliance_check_ext on inv_compliance_check (external_ref);
create index ix_inv_compliance_check_rule_id on inv_compliance_check (tenant_id, rule_id);

comment on table inv_compliance_check is 'Compliance Check - Compliance and Audit';
