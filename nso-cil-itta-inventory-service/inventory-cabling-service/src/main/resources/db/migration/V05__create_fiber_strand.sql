-- Fiber Strand (Cabling)
create table inv_fiber_strand (
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
    cable_id uuid not null,
    strand_number integer not null,
    buffer_tube varchar(255),
    tube_color varchar(255),
    attenuation_db numeric(18,4),
    in_use boolean,
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    constraint uk_inv_fiber_strand_tenant_code unique (tenant_id, code)
);

create index ix_inv_fiber_strand_status on inv_fiber_strand (tenant_id, status);
create index ix_inv_fiber_strand_name on inv_fiber_strand (tenant_id, name);
create index ix_inv_fiber_strand_ext on inv_fiber_strand (external_ref);
create index ix_inv_fiber_strand_cable_id on inv_fiber_strand (tenant_id, cable_id);

comment on table inv_fiber_strand is 'Fiber Strand - Cabling';
