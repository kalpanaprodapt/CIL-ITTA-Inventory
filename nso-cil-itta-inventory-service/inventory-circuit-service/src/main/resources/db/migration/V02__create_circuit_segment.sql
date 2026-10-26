-- Circuit Segment (Circuits)
create table inv_circuit_segment (
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
    circuit_id uuid not null,
    segment_order integer not null,
    a_termination_id uuid,
    z_termination_id uuid,
    latency_ms numeric(18,4),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    constraint uk_inv_circuit_segment_tenant_code unique (tenant_id, code)
);

create index ix_inv_circuit_segment_status on inv_circuit_segment (tenant_id, status);
create index ix_inv_circuit_segment_name on inv_circuit_segment (tenant_id, name);
create index ix_inv_circuit_segment_ext on inv_circuit_segment (external_ref);
create index ix_inv_circuit_segment_circuit_id on inv_circuit_segment (tenant_id, circuit_id);
create index ix_inv_circuit_segment_a_termination_id on inv_circuit_segment (tenant_id, a_termination_id);
create index ix_inv_circuit_segment_z_termination_id on inv_circuit_segment (tenant_id, z_termination_id);

comment on table inv_circuit_segment is 'Circuit Segment - Circuits';
