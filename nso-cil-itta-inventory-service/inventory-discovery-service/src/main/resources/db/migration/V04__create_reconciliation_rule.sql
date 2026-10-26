-- Reconciliation Rule (Discovery and Reconciliation)
create table inv_reconciliation_rule (
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
    rule_name varchar(255) not null,
    match_key varchar(255) not null,
    action varchar(40),
    priority integer not null,
    expression varchar(4000),
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    constraint uk_inv_reconciliation_rule_tenant_code unique (tenant_id, code)
);

create index ix_inv_reconciliation_rule_status on inv_reconciliation_rule (tenant_id, status);
create index ix_inv_reconciliation_rule_name on inv_reconciliation_rule (tenant_id, name);
create index ix_inv_reconciliation_rule_ext on inv_reconciliation_rule (external_ref);


comment on table inv_reconciliation_rule is 'Reconciliation Rule - Discovery and Reconciliation';
