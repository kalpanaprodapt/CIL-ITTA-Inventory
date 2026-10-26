-- Compliance Exception (Compliance and Audit)
create table inv_compliance_exception (
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
    justification varchar(4000) not null,
    approved_by varchar(255),
    expires_on date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    constraint uk_inv_compliance_exception_tenant_code unique (tenant_id, code)
);

create index ix_inv_compliance_exception_status on inv_compliance_exception (tenant_id, status);
create index ix_inv_compliance_exception_name on inv_compliance_exception (tenant_id, name);
create index ix_inv_compliance_exception_ext on inv_compliance_exception (external_ref);
create index ix_inv_compliance_exception_rule_id on inv_compliance_exception (tenant_id, rule_id);

comment on table inv_compliance_exception is 'Compliance Exception - Compliance and Audit';
