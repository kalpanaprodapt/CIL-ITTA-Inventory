-- Bootstrap for the Network Device schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_device') then
    create role svc_device login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_device;
grant usage on schema public to svc_device;

-- tables are created by Flyway from inventory-device-service; grants applied afterwards
grant select, insert, update, delete on inv_network_device to svc_device;
grant select, insert, update, delete on inv_chassis to svc_device;
grant select, insert, update, delete on inv_line_card to svc_device;
grant select, insert, update, delete on inv_power_supply to svc_device;
grant select, insert, update, delete on inv_fan_tray to svc_device;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_device;
