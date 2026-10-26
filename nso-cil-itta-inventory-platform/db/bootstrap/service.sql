-- Bootstrap for the Services schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_service') then
    create role svc_service login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_service;
grant usage on schema public to svc_service;

-- tables are created by Flyway from inventory-service-service; grants applied afterwards
grant select, insert, update, delete on inv_service_catalog_item to svc_service;
grant select, insert, update, delete on inv_service_instance to svc_service;
grant select, insert, update, delete on inv_service_order to svc_service;
grant select, insert, update, delete on inv_service_dependency to svc_service;
grant select, insert, update, delete on inv_service_endpoint to svc_service;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_service;
