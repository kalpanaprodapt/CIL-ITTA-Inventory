-- Suppression Rule (Alarms and Events)
create table inv_suppression_rule (
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
    match_expression varchar(4000) not null,
    starts_at timestamptz,
    ends_at timestamptz,
    reason varchar(255),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    constraint uk_inv_suppression_rule_tenant_code unique (tenant_id, code)
);

create index ix_inv_suppression_rule_status on inv_suppression_rule (tenant_id, status);
create index ix_inv_suppression_rule_name on inv_suppression_rule (tenant_id, name);
create index ix_inv_suppression_rule_ext on inv_suppression_rule (external_ref);


comment on table inv_suppression_rule is 'Suppression Rule - Alarms and Events';
