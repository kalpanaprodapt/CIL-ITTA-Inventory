-- Invoice (Asset and Finance)
create table inv_invoice (
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
    invoice_number varchar(255) not null,
    po_id uuid,
    amount numeric(18,4) not null,
    due_date date,
    paid boolean,
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    labels varchar(255),
    constraint uk_inv_invoice_tenant_code unique (tenant_id, code)
);

create index ix_inv_invoice_status on inv_invoice (tenant_id, status);
create index ix_inv_invoice_name on inv_invoice (tenant_id, name);
create index ix_inv_invoice_ext on inv_invoice (external_ref);
create index ix_inv_invoice_po_id on inv_invoice (tenant_id, po_id);

comment on table inv_invoice is 'Invoice - Asset and Finance';
