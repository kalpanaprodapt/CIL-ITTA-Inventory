-- Alarm Definition (Alarms and Events)
create table inv_alarm_definition (
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
    alarm_code varchar(255) not null,
    severity varchar(40),
    probable_cause varchar(255),
    auto_clear boolean,
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    constraint uk_inv_alarm_definition_tenant_code unique (tenant_id, code)
);

create index ix_inv_alarm_definition_status on inv_alarm_definition (tenant_id, status);
create index ix_inv_alarm_definition_name on inv_alarm_definition (tenant_id, name);
create index ix_inv_alarm_definition_ext on inv_alarm_definition (external_ref);


comment on table inv_alarm_definition is 'Alarm Definition - Alarms and Events';
