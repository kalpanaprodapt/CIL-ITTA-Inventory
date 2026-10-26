-- Sub Interface (Port and Interface)
create table inv_sub_interface (
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
    interface_id uuid not null,
    vlan_tag integer not null,
    encapsulation varchar(255),
    ipv4_address varchar(255),
    ipv6_address varchar(255),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    constraint uk_inv_sub_interface_tenant_code unique (tenant_id, code)
);

create index ix_inv_sub_interface_status on inv_sub_interface (tenant_id, status);
create index ix_inv_sub_interface_name on inv_sub_interface (tenant_id, name);
create index ix_inv_sub_interface_ext on inv_sub_interface (external_ref);
create index ix_inv_sub_interface_interface_id on inv_sub_interface (tenant_id, interface_id);

comment on table inv_sub_interface is 'Sub Interface - Port and Interface';
