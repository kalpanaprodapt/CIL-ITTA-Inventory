-- Warranty (Vendor and Model)
create table inv_warranty (
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
    asset_id uuid not null,
    start_date date not null,
    end_date date not null,
    warranty_type varchar(40),
    terms varchar(4000),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    constraint uk_inv_warranty_tenant_code unique (tenant_id, code)
);

create index ix_inv_warranty_status on inv_warranty (tenant_id, status);
create index ix_inv_warranty_name on inv_warranty (tenant_id, name);
create index ix_inv_warranty_ext on inv_warranty (external_ref);
create index ix_inv_warranty_asset_id on inv_warranty (tenant_id, asset_id);

comment on table inv_warranty is 'Warranty - Vendor and Model';
