-- Alarm Instance (Alarms and Events)
create table inv_alarm_instance (
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
    definition_id uuid not null,
    source_ref varchar(255) not null,
    raised_at timestamptz not null,
    cleared_at timestamptz,
    acked_by varchar(255),
    alarm_state varchar(40),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    constraint uk_inv_alarm_instance_tenant_code unique (tenant_id, code)
);

create index ix_inv_alarm_instance_status on inv_alarm_instance (tenant_id, status);
create index ix_inv_alarm_instance_name on inv_alarm_instance (tenant_id, name);
create index ix_inv_alarm_instance_ext on inv_alarm_instance (external_ref);
create index ix_inv_alarm_instance_definition_id on inv_alarm_instance (tenant_id, definition_id);

comment on table inv_alarm_instance is 'Alarm Instance - Alarms and Events';
