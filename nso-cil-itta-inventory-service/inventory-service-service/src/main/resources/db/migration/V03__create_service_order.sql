-- Service Order (Services)
create table inv_service_order (
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
    customer_id uuid not null,
    order_type varchar(40),
    requested_on date not null,
    target_date date,
    priority integer,
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    constraint uk_inv_service_order_tenant_code unique (tenant_id, code)
);

create index ix_inv_service_order_status on inv_service_order (tenant_id, status);
create index ix_inv_service_order_name on inv_service_order (tenant_id, name);
create index ix_inv_service_order_ext on inv_service_order (external_ref);
create index ix_inv_service_order_customer_id on inv_service_order (tenant_id, customer_id);

comment on table inv_service_order is 'Service Order - Services';
