-- Ospf Area (L2 and L3 Routing)
create table inv_ospf_area (
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
    area_id varchar(255) not null,
    area_type varchar(40),
    device_count integer,
    auth_type varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    constraint uk_inv_ospf_area_tenant_code unique (tenant_id, code)
);

create index ix_inv_ospf_area_status on inv_ospf_area (tenant_id, status);
create index ix_inv_ospf_area_name on inv_ospf_area (tenant_id, name);
create index ix_inv_ospf_area_ext on inv_ospf_area (external_ref);


comment on table inv_ospf_area is 'Ospf Area - L2 and L3 Routing';
