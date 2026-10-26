-- Service Dependency (Services)
create table inv_service_dependency (
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
    service_id uuid not null,
    depends_on_id uuid not null,
    dependency_type varchar(40),
    impact_score numeric(18,4),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    constraint uk_inv_service_dependency_tenant_code unique (tenant_id, code)
);

create index ix_inv_service_dependency_status on inv_service_dependency (tenant_id, status);
create index ix_inv_service_dependency_name on inv_service_dependency (tenant_id, name);
create index ix_inv_service_dependency_ext on inv_service_dependency (external_ref);
create index ix_inv_service_dependency_service_id on inv_service_dependency (tenant_id, service_id);
create index ix_inv_service_dependency_depends_on_id on inv_service_dependency (tenant_id, depends_on_id);

comment on table inv_service_dependency is 'Service Dependency - Services';
