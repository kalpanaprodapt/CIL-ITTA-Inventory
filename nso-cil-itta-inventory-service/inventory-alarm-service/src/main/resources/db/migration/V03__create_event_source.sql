-- Event Source (Alarms and Events)
create table inv_event_source (
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
    source_name varchar(255) not null,
    protocol varchar(40),
    endpoint varchar(255),
    heartbeat_seconds integer,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    constraint uk_inv_event_source_tenant_code unique (tenant_id, code)
);

create index ix_inv_event_source_status on inv_event_source (tenant_id, status);
create index ix_inv_event_source_name on inv_event_source (tenant_id, name);
create index ix_inv_event_source_ext on inv_event_source (external_ref);


comment on table inv_event_source is 'Event Source - Alarms and Events';
