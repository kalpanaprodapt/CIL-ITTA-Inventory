-- Customer Contact (Customers and SLA)
create table inv_customer_contact (
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
    customer_id uuid not null,
    full_name varchar(255) not null,
    email varchar(255),
    phone varchar(255),
    contact_role varchar(40),
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    constraint uk_inv_customer_contact_tenant_code unique (tenant_id, code)
);

create index ix_inv_customer_contact_status on inv_customer_contact (tenant_id, status);
create index ix_inv_customer_contact_name on inv_customer_contact (tenant_id, name);
create index ix_inv_customer_contact_ext on inv_customer_contact (external_ref);
create index ix_inv_customer_contact_customer_id on inv_customer_contact (tenant_id, customer_id);

comment on table inv_customer_contact is 'Customer Contact - Customers and SLA';
