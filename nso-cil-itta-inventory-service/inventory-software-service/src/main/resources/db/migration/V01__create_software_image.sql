-- Software Image (Software and Licensing)
create table inv_software_image (
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
    vendor_id uuid not null,
    image_name varchar(255) not null,
    size_mb bigint,
    checksum varchar(255),
    platform varchar(255),
    storage_path varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    constraint uk_inv_software_image_tenant_code unique (tenant_id, code)
);

create index ix_inv_software_image_status on inv_software_image (tenant_id, status);
create index ix_inv_software_image_name on inv_software_image (tenant_id, name);
create index ix_inv_software_image_ext on inv_software_image (external_ref);
create index ix_inv_software_image_vendor_id on inv_software_image (tenant_id, vendor_id);

comment on table inv_software_image is 'Software Image - Software and Licensing';
