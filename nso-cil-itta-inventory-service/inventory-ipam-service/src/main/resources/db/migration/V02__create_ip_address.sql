-- Ip Address (IP Management)
create table inv_ip_address (
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
    subnet_id uuid not null,
    address varchar(255) not null,
    assigned_to_id uuid,
    assignment_type varchar(40),
    dns_name varchar(255),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    constraint uk_inv_ip_address_tenant_code unique (tenant_id, code)
);

create index ix_inv_ip_address_status on inv_ip_address (tenant_id, status);
create index ix_inv_ip_address_name on inv_ip_address (tenant_id, name);
create index ix_inv_ip_address_ext on inv_ip_address (external_ref);
create index ix_inv_ip_address_subnet_id on inv_ip_address (tenant_id, subnet_id);
create index ix_inv_ip_address_assigned_to_id on inv_ip_address (tenant_id, assigned_to_id);

comment on table inv_ip_address is 'Ip Address - IP Management';
