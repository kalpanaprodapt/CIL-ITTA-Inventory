-- Software Version (Software and Licensing)
create table inv_software_version (
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
    image_id uuid not null,
    version_string varchar(255) not null,
    release_date date,
    end_of_support date,
    release_train varchar(40),
    cve_count integer,
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    constraint uk_inv_software_version_tenant_code unique (tenant_id, code)
);

create index ix_inv_software_version_status on inv_software_version (tenant_id, status);
create index ix_inv_software_version_name on inv_software_version (tenant_id, name);
create index ix_inv_software_version_ext on inv_software_version (external_ref);
create index ix_inv_software_version_image_id on inv_software_version (tenant_id, image_id);

comment on table inv_software_version is 'Software Version - Software and Licensing';
