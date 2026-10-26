-- Power Feed (Facility)
create table inv_power_feed (
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
    room_id uuid not null,
    feed_type varchar(40),
    voltage numeric(18,4),
    amperage numeric(18,4),
    phase varchar(255),
    breaker_label varchar(255),
    redundancy_group varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    constraint uk_inv_power_feed_tenant_code unique (tenant_id, code)
);

create index ix_inv_power_feed_status on inv_power_feed (tenant_id, status);
create index ix_inv_power_feed_name on inv_power_feed (tenant_id, name);
create index ix_inv_power_feed_ext on inv_power_feed (external_ref);
create index ix_inv_power_feed_room_id on inv_power_feed (tenant_id, room_id);

comment on table inv_power_feed is 'Power Feed - Facility';
