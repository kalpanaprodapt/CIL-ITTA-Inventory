-- Bootstrap for the Facility schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_facility') then
    create role svc_facility login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_facility;
grant usage on schema public to svc_facility;

-- tables are created by Flyway from inventory-facility-service; grants applied afterwards
grant select, insert, update, delete on inv_floor to svc_facility;
grant select, insert, update, delete on inv_room to svc_facility;
grant select, insert, update, delete on inv_rack to svc_facility;
grant select, insert, update, delete on inv_rack_unit to svc_facility;
grant select, insert, update, delete on inv_power_feed to svc_facility;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_facility;
