-- Asset Tag (Asset and Finance)
create table inv_asset_tag (
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
    tag_value varchar(255) not null,
    tag_type varchar(40),
    printed_on date,
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    constraint uk_inv_asset_tag_tenant_code unique (tenant_id, code)
);

create index ix_inv_asset_tag_status on inv_asset_tag (tenant_id, status);
create index ix_inv_asset_tag_name on inv_asset_tag (tenant_id, name);
create index ix_inv_asset_tag_ext on inv_asset_tag (external_ref);
create index ix_inv_asset_tag_asset_id on inv_asset_tag (tenant_id, asset_id);

comment on table inv_asset_tag is 'Asset Tag - Asset and Finance';
