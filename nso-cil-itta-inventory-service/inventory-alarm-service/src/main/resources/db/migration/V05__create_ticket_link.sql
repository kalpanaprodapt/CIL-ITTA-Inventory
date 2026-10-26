-- Ticket Link (Alarms and Events)
create table inv_ticket_link (
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
    alarm_id uuid not null,
    ticket_system varchar(40),
    ticket_number varchar(255) not null,
    linked_at timestamptz,
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    constraint uk_inv_ticket_link_tenant_code unique (tenant_id, code)
);

create index ix_inv_ticket_link_status on inv_ticket_link (tenant_id, status);
create index ix_inv_ticket_link_name on inv_ticket_link (tenant_id, name);
create index ix_inv_ticket_link_ext on inv_ticket_link (external_ref);
create index ix_inv_ticket_link_alarm_id on inv_ticket_link (tenant_id, alarm_id);

comment on table inv_ticket_link is 'Ticket Link - Alarms and Events';
