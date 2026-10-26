-- Field Task (Change and Work Management)
create table inv_field_task (
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
    work_order_id uuid not null,
    task_name varchar(255) not null,
    sequence integer not null,
    duration_minutes integer,
    completed_on timestamptz,
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    constraint uk_inv_field_task_tenant_code unique (tenant_id, code)
);

create index ix_inv_field_task_status on inv_field_task (tenant_id, status);
create index ix_inv_field_task_name on inv_field_task (tenant_id, name);
create index ix_inv_field_task_ext on inv_field_task (external_ref);
create index ix_inv_field_task_work_order_id on inv_field_task (tenant_id, work_order_id);

comment on table inv_field_task is 'Field Task - Change and Work Management';
