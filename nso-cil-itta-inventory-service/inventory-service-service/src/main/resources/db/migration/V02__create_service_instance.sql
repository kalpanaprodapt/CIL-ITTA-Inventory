-- Service Instance (Services)
create table inv_service_instance (
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
    catalog_item_id uuid not null,
    customer_id uuid not null,
    activated_on date,
    bandwidth_mbps bigint,
    service_state varchar(40),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    constraint uk_inv_service_instance_tenant_code unique (tenant_id, code)
);

create index ix_inv_service_instance_status on inv_service_instance (tenant_id, status);
create index ix_inv_service_instance_name on inv_service_instance (tenant_id, name);
create index ix_inv_service_instance_ext on inv_service_instance (external_ref);
create index ix_inv_service_instance_catalog_item_id on inv_service_instance (tenant_id, catalog_item_id);
create index ix_inv_service_instance_customer_id on inv_service_instance (tenant_id, customer_id);

comment on table inv_service_instance is 'Service Instance - Services';
