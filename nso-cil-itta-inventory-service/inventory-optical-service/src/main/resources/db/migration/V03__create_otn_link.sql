-- Otn Link (Optical)
create table inv_otn_link (
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
    a_node_id uuid not null,
    z_node_id uuid not null,
    odu varchar(40),
    latency_us numeric(18,4),
    protection varchar(255),
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    constraint uk_inv_otn_link_tenant_code unique (tenant_id, code)
);

create index ix_inv_otn_link_status on inv_otn_link (tenant_id, status);
create index ix_inv_otn_link_name on inv_otn_link (tenant_id, name);
create index ix_inv_otn_link_ext on inv_otn_link (external_ref);
create index ix_inv_otn_link_a_node_id on inv_otn_link (tenant_id, a_node_id);
create index ix_inv_otn_link_z_node_id on inv_otn_link (tenant_id, z_node_id);

comment on table inv_otn_link is 'Otn Link - Optical';
