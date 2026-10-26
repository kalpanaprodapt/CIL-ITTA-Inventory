-- Bootstrap for the Radio and Mobile schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_radio') then
    create role svc_radio login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_radio;
grant usage on schema public to svc_radio;

-- tables are created by Flyway from inventory-radio-service; grants applied afterwards
grant select, insert, update, delete on inv_radio_unit to svc_radio;
grant select, insert, update, delete on inv_base_station to svc_radio;
grant select, insert, update, delete on inv_antenna to svc_radio;
grant select, insert, update, delete on inv_sector to svc_radio;
grant select, insert, update, delete on inv_backhaul_link to svc_radio;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_radio;
