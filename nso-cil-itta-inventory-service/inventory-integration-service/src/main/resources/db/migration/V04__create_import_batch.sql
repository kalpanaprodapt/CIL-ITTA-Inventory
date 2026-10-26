-- Import Batch (Integration Layer)
create table inv_import_batch (
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
    file_name varchar(255) not null,
    entity_type varchar(255) not null,
    total_rows integer,
    processed_rows integer,
    failed_rows integer,
    batch_state varchar(40),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    constraint uk_inv_import_batch_tenant_code unique (tenant_id, code)
);

create index ix_inv_import_batch_status on inv_import_batch (tenant_id, status);
create index ix_inv_import_batch_name on inv_import_batch (tenant_id, name);
create index ix_inv_import_batch_ext on inv_import_batch (external_ref);


comment on table inv_import_batch is 'Import Batch - Integration Layer';
