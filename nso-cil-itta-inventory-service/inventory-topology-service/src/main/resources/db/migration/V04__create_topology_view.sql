-- Topology View (Topology and Links)
create table inv_topology_view (
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
    view_name varchar(255) not null,
    view_type varchar(40),
    scope_filter varchar(255),
    refreshed_at timestamptz,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    constraint uk_inv_topology_view_tenant_code unique (tenant_id, code)
);

create index ix_inv_topology_view_status on inv_topology_view (tenant_id, status);
create index ix_inv_topology_view_name on inv_topology_view (tenant_id, name);
create index ix_inv_topology_view_ext on inv_topology_view (external_ref);


comment on table inv_topology_view is 'Topology View - Topology and Links';
