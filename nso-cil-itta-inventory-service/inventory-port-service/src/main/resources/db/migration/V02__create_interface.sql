-- Interface (Port and Interface)
create table inv_interface (
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
    port_id uuid not null,
    if_name varchar(255) not null,
    if_index integer,
    mac_address varchar(255),
    mtu integer,
    oper_state varchar(40),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    constraint uk_inv_interface_tenant_code unique (tenant_id, code)
);

create index ix_inv_interface_status on inv_interface (tenant_id, status);
create index ix_inv_interface_name on inv_interface (tenant_id, name);
create index ix_inv_interface_ext on inv_interface (external_ref);
create index ix_inv_interface_port_id on inv_interface (tenant_id, port_id);

comment on table inv_interface is 'Interface - Port and Interface';
