-- Change Request (Change and Work Management)
create table inv_change_request (
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
    change_number varchar(255) not null,
    change_type varchar(40),
    risk_level varchar(40),
    planned_start timestamptz,
    planned_end timestamptz,
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    constraint uk_inv_change_request_tenant_code unique (tenant_id, code)
);

create index ix_inv_change_request_status on inv_change_request (tenant_id, status);
create index ix_inv_change_request_name on inv_change_request (tenant_id, name);
create index ix_inv_change_request_ext on inv_change_request (external_ref);


comment on table inv_change_request is 'Change Request - Change and Work Management';
