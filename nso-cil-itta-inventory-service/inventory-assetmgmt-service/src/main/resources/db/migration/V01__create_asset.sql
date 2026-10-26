-- Asset (Asset and Finance)
create table inv_asset (
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
    asset_tag varchar(255) not null,
    asset_class varchar(255) not null,
    acquired_on date,
    purchase_price numeric(18,4),
    current_value numeric(18,4),
    custodian varchar(255),
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    constraint uk_inv_asset_tenant_code unique (tenant_id, code)
);

create index ix_inv_asset_status on inv_asset (tenant_id, status);
create index ix_inv_asset_name on inv_asset (tenant_id, name);
create index ix_inv_asset_ext on inv_asset (external_ref);


comment on table inv_asset is 'Asset - Asset and Finance';
