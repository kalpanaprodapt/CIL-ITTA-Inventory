-- Port (Port and Interface)
create table inv_port (
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
    device_id uuid not null,
    card_id uuid,
    port_number varchar(255) not null,
    speed_mbps bigint,
    media_type varchar(40),
    admin_state varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    constraint uk_inv_port_tenant_code unique (tenant_id, code)
);

create index ix_inv_port_status on inv_port (tenant_id, status);
create index ix_inv_port_name on inv_port (tenant_id, name);
create index ix_inv_port_ext on inv_port (external_ref);
create index ix_inv_port_device_id on inv_port (tenant_id, device_id);
create index ix_inv_port_card_id on inv_port (tenant_id, card_id);

comment on table inv_port is 'Port - Port and Interface';
