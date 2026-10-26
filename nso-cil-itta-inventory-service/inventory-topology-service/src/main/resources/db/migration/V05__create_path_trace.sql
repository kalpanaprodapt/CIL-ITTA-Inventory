-- Path Trace (Topology and Links)
create table inv_path_trace (
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
    source_ref varchar(255) not null,
    target_ref varchar(255) not null,
    hop_count integer,
    total_latency_ms numeric(18,4),
    trace_method varchar(255),
    traced_at timestamptz,
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    constraint uk_inv_path_trace_tenant_code unique (tenant_id, code)
);

create index ix_inv_path_trace_status on inv_path_trace (tenant_id, status);
create index ix_inv_path_trace_name on inv_path_trace (tenant_id, name);
create index ix_inv_path_trace_ext on inv_path_trace (external_ref);


comment on table inv_path_trace is 'Path Trace - Topology and Links';
