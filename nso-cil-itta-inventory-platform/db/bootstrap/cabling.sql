-- Bootstrap for the Cabling schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_cabling') then
    create role svc_cabling login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_cabling;
grant usage on schema public to svc_cabling;

-- tables are created by Flyway from inventory-cabling-service; grants applied afterwards
grant select, insert, update, delete on inv_cable to svc_cabling;
grant select, insert, update, delete on inv_patch_panel to svc_cabling;
grant select, insert, update, delete on inv_patch_port to svc_cabling;
grant select, insert, update, delete on inv_splice to svc_cabling;
grant select, insert, update, delete on inv_fiber_strand to svc_cabling;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_cabling;
