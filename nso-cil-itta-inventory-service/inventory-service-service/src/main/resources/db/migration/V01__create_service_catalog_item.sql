-- Service Catalog Item (Services)
create table inv_service_catalog_item (
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
    item_name varchar(255) not null,
    family varchar(40),
    base_price numeric(18,4),
    billing_unit varchar(255),
    orderable boolean,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    constraint uk_inv_service_catalog_item_tenant_code unique (tenant_id, code)
);

create index ix_inv_service_catalog_item_status on inv_service_catalog_item (tenant_id, status);
create index ix_inv_service_catalog_item_name on inv_service_catalog_item (tenant_id, name);
create index ix_inv_service_catalog_item_ext on inv_service_catalog_item (external_ref);


comment on table inv_service_catalog_item is 'Service Catalog Item - Services';
