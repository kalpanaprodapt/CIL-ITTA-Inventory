-- Technician (Change and Work Management)
create table inv_technician (
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
    employee_id varchar(255) not null,
    full_name varchar(255) not null,
    skill_set varchar(255),
    home_site_id uuid,
    certification_level integer,
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    constraint uk_inv_technician_tenant_code unique (tenant_id, code)
);

create index ix_inv_technician_status on inv_technician (tenant_id, status);
create index ix_inv_technician_name on inv_technician (tenant_id, name);
create index ix_inv_technician_ext on inv_technician (external_ref);
create index ix_inv_technician_home_site_id on inv_technician (tenant_id, home_site_id);

comment on table inv_technician is 'Technician - Change and Work Management';
