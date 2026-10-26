-- Bootstrap for the Vendor and Model schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_vendor') then
    create role svc_vendor login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_vendor;
grant usage on schema public to svc_vendor;

-- tables are created by Flyway from inventory-vendor-service; grants applied afterwards
grant select, insert, update, delete on inv_vendor to svc_vendor;
grant select, insert, update, delete on inv_hardware_model to svc_vendor;
grant select, insert, update, delete on inv_sku to svc_vendor;
grant select, insert, update, delete on inv_support_contract to svc_vendor;
grant select, insert, update, delete on inv_warranty to svc_vendor;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_vendor;
