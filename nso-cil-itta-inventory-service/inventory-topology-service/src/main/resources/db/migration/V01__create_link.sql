-- Link (Topology and Links)
create table inv_link (
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
    a_interface_id uuid not null,
    z_interface_id uuid not null,
    link_type varchar(40),
    speed_mbps bigint,
    discovered_by varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    constraint uk_inv_link_tenant_code unique (tenant_id, code)
);

create index ix_inv_link_status on inv_link (tenant_id, status);
create index ix_inv_link_name on inv_link (tenant_id, name);
create index ix_inv_link_ext on inv_link (external_ref);
create index ix_inv_link_a_interface_id on inv_link (tenant_id, a_interface_id);
create index ix_inv_link_z_interface_id on inv_link (tenant_id, z_interface_id);

comment on table inv_link is 'Link - Topology and Links';
