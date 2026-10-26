-- Policy Document (Compliance and Audit)
create table inv_policy_document (
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
    title varchar(255) not null,
    doc_version varchar(255) not null,
    effective_date date not null,
    owner varchar(255),
    document_url varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    constraint uk_inv_policy_document_tenant_code unique (tenant_id, code)
);

create index ix_inv_policy_document_status on inv_policy_document (tenant_id, status);
create index ix_inv_policy_document_name on inv_policy_document (tenant_id, name);
create index ix_inv_policy_document_ext on inv_policy_document (external_ref);


comment on table inv_policy_document is 'Policy Document - Compliance and Audit';
