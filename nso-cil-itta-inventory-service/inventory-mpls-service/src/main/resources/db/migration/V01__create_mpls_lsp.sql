-- Mpls Lsp (MPLS and VPN)
create table inv_mpls_lsp (
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
    lsp_name varchar(255) not null,
    head_end_id uuid not null,
    tail_end_id uuid not null,
    bandwidth_mbps bigint,
    setup_priority integer,
    path_type varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    constraint uk_inv_mpls_lsp_tenant_code unique (tenant_id, code)
);

create index ix_inv_mpls_lsp_status on inv_mpls_lsp (tenant_id, status);
create index ix_inv_mpls_lsp_name on inv_mpls_lsp (tenant_id, name);
create index ix_inv_mpls_lsp_ext on inv_mpls_lsp (external_ref);
create index ix_inv_mpls_lsp_head_end_id on inv_mpls_lsp (tenant_id, head_end_id);
create index ix_inv_mpls_lsp_tail_end_id on inv_mpls_lsp (tenant_id, tail_end_id);

comment on table inv_mpls_lsp is 'Mpls Lsp - MPLS and VPN';
