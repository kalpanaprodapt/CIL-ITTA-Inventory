-- Circuit Provider (Circuits)
create table inv_circuit_provider (
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
    provider_name varchar(255) not null,
    account_number varchar(255),
    noc varchar(255),
    escalation_email varchar(255),
    provider_type varchar(40),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    constraint uk_inv_circuit_provider_tenant_code unique (tenant_id, code)
);

create index ix_inv_circuit_provider_status on inv_circuit_provider (tenant_id, status);
create index ix_inv_circuit_provider_name on inv_circuit_provider (tenant_id, name);
create index ix_inv_circuit_provider_ext on inv_circuit_provider (external_ref);


comment on table inv_circuit_provider is 'Circuit Provider - Circuits';
