-- Reconciliation Result (Discovery and Reconciliation)
create table inv_reconciliation_result (
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
    job_id uuid not null,
    rule_id uuid,
    outcome varchar(40),
    diff_summary varchar(4000),
    resolved_by varchar(255),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    constraint uk_inv_reconciliation_result_tenant_code unique (tenant_id, code)
);

create index ix_inv_reconciliation_result_status on inv_reconciliation_result (tenant_id, status);
create index ix_inv_reconciliation_result_name on inv_reconciliation_result (tenant_id, name);
create index ix_inv_reconciliation_result_ext on inv_reconciliation_result (external_ref);
create index ix_inv_reconciliation_result_job_id on inv_reconciliation_result (tenant_id, job_id);
create index ix_inv_reconciliation_result_rule_id on inv_reconciliation_result (tenant_id, rule_id);

comment on table inv_reconciliation_result is 'Reconciliation Result - Discovery and Reconciliation';
