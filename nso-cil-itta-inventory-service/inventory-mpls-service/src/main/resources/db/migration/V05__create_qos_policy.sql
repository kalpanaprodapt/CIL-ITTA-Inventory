-- Qos Policy (MPLS and VPN)
create table inv_qos_policy (
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
    policy_name varchar(255) not null,
    class_count integer not null,
    cir_mbps bigint,
    pir_mbps bigint,
    marking_scheme varchar(255),
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    constraint uk_inv_qos_policy_tenant_code unique (tenant_id, code)
);

create index ix_inv_qos_policy_status on inv_qos_policy (tenant_id, status);
create index ix_inv_qos_policy_name on inv_qos_policy (tenant_id, name);
create index ix_inv_qos_policy_ext on inv_qos_policy (external_ref);


comment on table inv_qos_policy is 'Qos Policy - MPLS and VPN';
