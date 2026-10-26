-- Splice (Cabling)
create table inv_splice (
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
    enclosure_id varchar(255) not null,
    a_strand_id uuid,
    z_strand_id uuid,
    loss_db numeric(18,4),
    spliced_on date,
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    constraint uk_inv_splice_tenant_code unique (tenant_id, code)
);

create index ix_inv_splice_status on inv_splice (tenant_id, status);
create index ix_inv_splice_name on inv_splice (tenant_id, name);
create index ix_inv_splice_ext on inv_splice (external_ref);
create index ix_inv_splice_a_strand_id on inv_splice (tenant_id, a_strand_id);
create index ix_inv_splice_z_strand_id on inv_splice (tenant_id, z_strand_id);

comment on table inv_splice is 'Splice - Cabling';
