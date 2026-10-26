-- Virtual Machine (Virtual and Cloud)
create table inv_virtual_machine (
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
    hypervisor_id uuid not null,
    vm_name varchar(255) not null,
    vcpu integer not null,
    memory_gb integer not null,
    disk_gb integer,
    power_state varchar(40),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    constraint uk_inv_virtual_machine_tenant_code unique (tenant_id, code)
);

create index ix_inv_virtual_machine_status on inv_virtual_machine (tenant_id, status);
create index ix_inv_virtual_machine_name on inv_virtual_machine (tenant_id, name);
create index ix_inv_virtual_machine_ext on inv_virtual_machine (external_ref);
create index ix_inv_virtual_machine_hypervisor_id on inv_virtual_machine (tenant_id, hypervisor_id);

comment on table inv_virtual_machine is 'Virtual Machine - Virtual and Cloud';
