-- Roadm (Optical)
create table inv_roadm (
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
    system_id uuid not null,
    degrees integer not null,
    colorless boolean,
    directionless boolean,
    contentionless boolean,
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    constraint uk_inv_roadm_tenant_code unique (tenant_id, code)
);

create index ix_inv_roadm_status on inv_roadm (tenant_id, status);
create index ix_inv_roadm_name on inv_roadm (tenant_id, name);
create index ix_inv_roadm_ext on inv_roadm (external_ref);
create index ix_inv_roadm_system_id on inv_roadm (tenant_id, system_id);

comment on table inv_roadm is 'Roadm - Optical';
