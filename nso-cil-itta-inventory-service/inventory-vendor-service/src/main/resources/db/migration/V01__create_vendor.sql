-- Vendor (Vendor and Model)
create table inv_vendor (
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
    legal_name varchar(255) not null,
    support_email varchar(255),
    support_phone varchar(255),
    website varchar(255),
    tier varchar(40),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    constraint uk_inv_vendor_tenant_code unique (tenant_id, code)
);

create index ix_inv_vendor_status on inv_vendor (tenant_id, status);
create index ix_inv_vendor_name on inv_vendor (tenant_id, name);
create index ix_inv_vendor_ext on inv_vendor (external_ref);


comment on table inv_vendor is 'Vendor - Vendor and Model';
