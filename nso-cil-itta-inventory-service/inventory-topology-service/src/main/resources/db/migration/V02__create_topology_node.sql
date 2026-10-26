-- Topology Node (Topology and Links)
create table inv_topology_node (
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
    entity_ref varchar(255) not null,
    node_type varchar(255),
    x_pos numeric(18,4),
    y_pos numeric(18,4),
    layer integer,
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    constraint uk_inv_topology_node_tenant_code unique (tenant_id, code)
);

create index ix_inv_topology_node_status on inv_topology_node (tenant_id, status);
create index ix_inv_topology_node_name on inv_topology_node (tenant_id, name);
create index ix_inv_topology_node_ext on inv_topology_node (external_ref);
create index ix_inv_topology_node_view_id on inv_topology_node (tenant_id, view_id);

comment on table inv_topology_node is 'Topology Node - Topology and Links';
