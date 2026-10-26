-- Forecast Model (Capacity Planning)
create table inv_forecast_model (
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
    model_name varchar(255) not null,
    algorithm varchar(40),
    trained_on timestamptz,
    mape numeric(18,4),
    criticality varchar(40),
    cost_center varchar(255),
    notes varchar(4000),
    effective_from date,
    effective_to date,
    constraint uk_inv_forecast_model_tenant_code unique (tenant_id, code)
);

create index ix_inv_forecast_model_status on inv_forecast_model (tenant_id, status);
create index ix_inv_forecast_model_name on inv_forecast_model (tenant_id, name);
create index ix_inv_forecast_model_ext on inv_forecast_model (external_ref);


comment on table inv_forecast_model is 'Forecast Model - Capacity Planning';
