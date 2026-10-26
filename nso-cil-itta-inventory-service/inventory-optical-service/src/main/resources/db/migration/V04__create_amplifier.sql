-- Amplifier (Optical)
create table inv_amplifier (
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
    system_id uuid not null,
    amp_type varchar(40),
    gain_db numeric(18,4) not null,
    output_power_dbm numeric(18,4),
    noise_figure_db numeric(18,4),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    constraint uk_inv_amplifier_tenant_code unique (tenant_id, code)
);

create index ix_inv_amplifier_status on inv_amplifier (tenant_id, status);
create index ix_inv_amplifier_name on inv_amplifier (tenant_id, name);
create index ix_inv_amplifier_ext on inv_amplifier (external_ref);
create index ix_inv_amplifier_system_id on inv_amplifier (tenant_id, system_id);

comment on table inv_amplifier is 'Amplifier - Optical';
