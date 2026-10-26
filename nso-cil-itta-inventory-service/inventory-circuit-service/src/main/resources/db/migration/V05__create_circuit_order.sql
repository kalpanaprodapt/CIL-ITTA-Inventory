-- Circuit Order (Circuits)
create table inv_circuit_order (
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
    circuit_id uuid,
    order_number varchar(255) not null,
    ordered_on date not null,
    requested_date date,
    committed_date date,
    order_status varchar(40),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    constraint uk_inv_circuit_order_tenant_code unique (tenant_id, code)
);

create index ix_inv_circuit_order_status on inv_circuit_order (tenant_id, status);
create index ix_inv_circuit_order_name on inv_circuit_order (tenant_id, name);
create index ix_inv_circuit_order_ext on inv_circuit_order (external_ref);
create index ix_inv_circuit_order_circuit_id on inv_circuit_order (tenant_id, circuit_id);

comment on table inv_circuit_order is 'Circuit Order - Circuits';
