-- Bootstrap for the Capacity Planning schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_capacity') then
    create role svc_capacity login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_capacity;
grant usage on schema public to svc_capacity;

-- tables are created by Flyway from inventory-capacity-service; grants applied afterwards
grant select, insert, update, delete on inv_capacity_plan to svc_capacity;
grant select, insert, update, delete on inv_utilization_sample to svc_capacity;
grant select, insert, update, delete on inv_threshold_policy to svc_capacity;
grant select, insert, update, delete on inv_forecast_model to svc_capacity;
grant select, insert, update, delete on inv_reservation to svc_capacity;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_capacity;
