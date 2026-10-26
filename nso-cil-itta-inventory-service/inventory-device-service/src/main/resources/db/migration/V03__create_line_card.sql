-- Line Card (Network Device)
create table inv_line_card (
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
    chassis_id uuid not null,
    slot_number integer not null,
    card_type varchar(255),
    port_count integer,
    serial_number varchar(255),
    firmware varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    constraint uk_inv_line_card_tenant_code unique (tenant_id, code)
);

create index ix_inv_line_card_status on inv_line_card (tenant_id, status);
create index ix_inv_line_card_name on inv_line_card (tenant_id, name);
create index ix_inv_line_card_ext on inv_line_card (external_ref);
create index ix_inv_line_card_chassis_id on inv_line_card (tenant_id, chassis_id);

comment on table inv_line_card is 'Line Card - Network Device';
