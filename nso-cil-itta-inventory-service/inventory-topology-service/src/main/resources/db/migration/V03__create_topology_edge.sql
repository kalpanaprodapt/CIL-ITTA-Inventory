-- Topology Edge (Topology and Links)
create table inv_topology_edge (
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
    view_id uuid not null,
    source_node_id uuid not null,
    target_node_id uuid not null,
    weight numeric(18,4),
    directed boolean,
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    constraint uk_inv_topology_edge_tenant_code unique (tenant_id, code)
);

create index ix_inv_topology_edge_status on inv_topology_edge (tenant_id, status);
create index ix_inv_topology_edge_name on inv_topology_edge (tenant_id, name);
create index ix_inv_topology_edge_ext on inv_topology_edge (external_ref);
create index ix_inv_topology_edge_view_id on inv_topology_edge (tenant_id, view_id);
create index ix_inv_topology_edge_source_node_id on inv_topology_edge (tenant_id, source_node_id);
create index ix_inv_topology_edge_target_node_id on inv_topology_edge (tenant_id, target_node_id);

comment on table inv_topology_edge is 'Topology Edge - Topology and Links';
