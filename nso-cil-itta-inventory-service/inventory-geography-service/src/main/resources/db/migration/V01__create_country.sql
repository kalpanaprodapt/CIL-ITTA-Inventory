-- Country (Geography)
create table inv_country (
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
    iso_code varchar(255) not null,
    iso_numeric integer,
    dial_code varchar(255),
    currency varchar(255),
    regulatory_body varchar(255),
    timezone varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    constraint uk_inv_country_tenant_code unique (tenant_id, code)
);

create index ix_inv_country_status on inv_country (tenant_id, status);
create index ix_inv_country_name on inv_country (tenant_id, name);
create index ix_inv_country_ext on inv_country (external_ref);


comment on table inv_country is 'Country - Geography';
