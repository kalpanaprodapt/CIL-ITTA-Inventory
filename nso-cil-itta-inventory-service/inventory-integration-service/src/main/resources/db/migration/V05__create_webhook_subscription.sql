-- Webhook Subscription (Integration Layer)
create table inv_webhook_subscription (
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
    callback_url varchar(255) not null,
    event_types varchar(255) not null,
    secret_ref varchar(255),
    active boolean,
    retry_limit integer,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    region varchar(255),
    owner varchar(255),
    constraint uk_inv_webhook_subscription_tenant_code unique (tenant_id, code)
);

create index ix_inv_webhook_subscription_status on inv_webhook_subscription (tenant_id, status);
create index ix_inv_webhook_subscription_name on inv_webhook_subscription (tenant_id, name);
create index ix_inv_webhook_subscription_ext on inv_webhook_subscription (external_ref);


comment on table inv_webhook_subscription is 'Webhook Subscription - Integration Layer';
