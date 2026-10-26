-- Pseudo Wire (MPLS and VPN)
create table inv_pseudo_wire (
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
    vc_id bigint not null,
    a_end_id uuid not null,
    z_end_id uuid not null,
    encapsulation varchar(255),
    control_word boolean,
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    constraint uk_inv_pseudo_wire_tenant_code unique (tenant_id, code)
);

create index ix_inv_pseudo_wire_status on inv_pseudo_wire (tenant_id, status);
create index ix_inv_pseudo_wire_name on inv_pseudo_wire (tenant_id, name);
create index ix_inv_pseudo_wire_ext on inv_pseudo_wire (external_ref);
create index ix_inv_pseudo_wire_a_end_id on inv_pseudo_wire (tenant_id, a_end_id);
create index ix_inv_pseudo_wire_z_end_id on inv_pseudo_wire (tenant_id, z_end_id);

comment on table inv_pseudo_wire is 'Pseudo Wire - MPLS and VPN';
