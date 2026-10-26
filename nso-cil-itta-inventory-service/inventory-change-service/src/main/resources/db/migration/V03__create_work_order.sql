-- Work Order (Change and Work Management)
create table inv_work_order (
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
    work_order_number varchar(255) not null,
    work_type varchar(255) not null,
    assigned_technician_id uuid,
    due_date date,
    work_state varchar(40),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    constraint uk_inv_work_order_tenant_code unique (tenant_id, code)
);

create index ix_inv_work_order_status on inv_work_order (tenant_id, status);
create index ix_inv_work_order_name on inv_work_order (tenant_id, name);
create index ix_inv_work_order_ext on inv_work_order (external_ref);
create index ix_inv_work_order_assigned_technician_id on inv_work_order (tenant_id, assigned_technician_id);

comment on table inv_work_order is 'Work Order - Change and Work Management';
