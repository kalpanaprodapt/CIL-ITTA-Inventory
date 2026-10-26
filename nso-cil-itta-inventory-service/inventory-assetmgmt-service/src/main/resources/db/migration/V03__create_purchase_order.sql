-- Purchase Order (Asset and Finance)
create table inv_purchase_order (
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
    po_number varchar(255) not null,
    vendor_id uuid not null,
    ordered_on date not null,
    total_amount numeric(18,4) not null,
    currency varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    constraint uk_inv_purchase_order_tenant_code unique (tenant_id, code)
);

create index ix_inv_purchase_order_status on inv_purchase_order (tenant_id, status);
create index ix_inv_purchase_order_name on inv_purchase_order (tenant_id, name);
create index ix_inv_purchase_order_ext on inv_purchase_order (external_ref);
create index ix_inv_purchase_order_vendor_id on inv_purchase_order (tenant_id, vendor_id);

comment on table inv_purchase_order is 'Purchase Order - Asset and Finance';
