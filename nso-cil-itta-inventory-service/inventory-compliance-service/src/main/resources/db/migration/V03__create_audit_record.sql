-- Audit Record (Compliance and Audit)
create table inv_audit_record (
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
    actor varchar(255) not null,
    action varchar(255) not null,
    entity_ref varchar(255) not null,
    occurred_at timestamptz not null,
    before_state varchar(4000),
    after_state varchar(4000),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    constraint uk_inv_audit_record_tenant_code unique (tenant_id, code)
);

create index ix_inv_audit_record_status on inv_audit_record (tenant_id, status);
create index ix_inv_audit_record_name on inv_audit_record (tenant_id, name);
create index ix_inv_audit_record_ext on inv_audit_record (external_ref);


comment on table inv_audit_record is 'Audit Record - Compliance and Audit';
