-- Dns Record (IP Management)
create table inv_dns_record (
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
    zone_name varchar(255) not null,
    record_type varchar(40),
    record_value varchar(255) not null,
    ttl integer,
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    criticality varchar(40),
    cost_center varchar(255),
    constraint uk_inv_dns_record_tenant_code unique (tenant_id, code)
);

create index ix_inv_dns_record_status on inv_dns_record (tenant_id, status);
create index ix_inv_dns_record_name on inv_dns_record (tenant_id, name);
create index ix_inv_dns_record_ext on inv_dns_record (external_ref);


comment on table inv_dns_record is 'Dns Record - IP Management';
