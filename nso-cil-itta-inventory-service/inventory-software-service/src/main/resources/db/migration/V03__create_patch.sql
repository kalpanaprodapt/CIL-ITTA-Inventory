-- Patch (Software and Licensing)
create table inv_patch (
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
    version_id uuid not null,
    patch_id varchar(255) not null,
    severity varchar(40),
    released_on date,
    reboot_required boolean,
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    constraint uk_inv_patch_tenant_code unique (tenant_id, code)
);

create index ix_inv_patch_status on inv_patch (tenant_id, status);
create index ix_inv_patch_name on inv_patch (tenant_id, name);
create index ix_inv_patch_ext on inv_patch (external_ref);
create index ix_inv_patch_version_id on inv_patch (tenant_id, version_id);

comment on table inv_patch is 'Patch - Software and Licensing';
