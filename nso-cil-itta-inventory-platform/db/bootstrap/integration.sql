-- Bootstrap for the Integration Layer schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_integration') then
    create role svc_integration login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_integration;
grant usage on schema public to svc_integration;

-- tables are created by Flyway from inventory-integration-service; grants applied afterwards
grant select, insert, update, delete on inv_integration_endpoint to svc_integration;
grant select, insert, update, delete on inv_sync_job to svc_integration;
grant select, insert, update, delete on inv_sync_mapping to svc_integration;
grant select, insert, update, delete on inv_import_batch to svc_integration;
grant select, insert, update, delete on inv_webhook_subscription to svc_integration;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_integration;
