-- Reservation (Capacity Planning)
create table inv_reservation (
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
    resource_ref varchar(255) not null,
    reserved_for varchar(255) not null,
    quantity numeric(18,4) not null,
    start_date date not null,
    end_date date,
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    constraint uk_inv_reservation_tenant_code unique (tenant_id, code)
);

create index ix_inv_reservation_status on inv_reservation (tenant_id, status);
create index ix_inv_reservation_name on inv_reservation (tenant_id, name);
create index ix_inv_reservation_ext on inv_reservation (external_ref);


comment on table inv_reservation is 'Reservation - Capacity Planning';
