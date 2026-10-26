-- Sku (Vendor and Model)
create table inv_sku (
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
    model_id uuid not null,
    sku_code varchar(255) not null,
    list_price numeric(18,4),
    currency varchar(255),
    lead_time_days integer,
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    constraint uk_inv_sku_tenant_code unique (tenant_id, code)
);

create index ix_inv_sku_status on inv_sku (tenant_id, status);
create index ix_inv_sku_name on inv_sku (tenant_id, name);
create index ix_inv_sku_ext on inv_sku (external_ref);
create index ix_inv_sku_model_id on inv_sku (tenant_id, model_id);

comment on table inv_sku is 'Sku - Vendor and Model';
