-- Bgp Session (L2 and L3 Routing)
create table inv_bgp_session (
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
    local_device_id uuid not null,
    peer_address varchar(255) not null,
    local_asn bigint not null,
    remote_asn bigint not null,
    session_state varchar(40),
    prefix_limit integer,
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    constraint uk_inv_bgp_session_tenant_code unique (tenant_id, code)
);

create index ix_inv_bgp_session_status on inv_bgp_session (tenant_id, status);
create index ix_inv_bgp_session_name on inv_bgp_session (tenant_id, name);
create index ix_inv_bgp_session_ext on inv_bgp_session (external_ref);
create index ix_inv_bgp_session_local_device_id on inv_bgp_session (tenant_id, local_device_id);

comment on table inv_bgp_session is 'Bgp Session - L2 and L3 Routing';
