-- Circuit Termination (Circuits)
create table inv_circuit_termination (
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
    side varchar(40),
    port_id uuid not null,
    xconnect_id varchar(255),
    patch_info varchar(255),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    constraint uk_inv_circuit_termination_tenant_code unique (tenant_id, code)
);

create index ix_inv_circuit_termination_status on inv_circuit_termination (tenant_id, status);
create index ix_inv_circuit_termination_name on inv_circuit_termination (tenant_id, name);
create index ix_inv_circuit_termination_ext on inv_circuit_termination (external_ref);
create index ix_inv_circuit_termination_circuit_id on inv_circuit_termination (tenant_id, circuit_id);
create index ix_inv_circuit_termination_port_id on inv_circuit_termination (tenant_id, port_id);

comment on table inv_circuit_termination is 'Circuit Termination - Circuits';
