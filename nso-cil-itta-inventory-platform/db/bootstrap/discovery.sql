-- Bootstrap for the Discovery and Reconciliation schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_discovery') then
    create role svc_discovery login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_discovery;
grant usage on schema public to svc_discovery;

-- tables are created by Flyway from inventory-discovery-service; grants applied afterwards
grant select, insert, update, delete on inv_discovery_job to svc_discovery;
grant select, insert, update, delete on inv_discovery_profile to svc_discovery;
grant select, insert, update, delete on inv_discovered_asset to svc_discovery;
grant select, insert, update, delete on inv_reconciliation_rule to svc_discovery;
grant select, insert, update, delete on inv_reconciliation_result to svc_discovery;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_discovery;
