-- Bootstrap for the Port and Interface schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_port') then
    create role svc_port login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_port;
grant usage on schema public to svc_port;

-- tables are created by Flyway from inventory-port-service; grants applied afterwards
grant select, insert, update, delete on inv_port to svc_port;
grant select, insert, update, delete on inv_interface to svc_port;
grant select, insert, update, delete on inv_sub_interface to svc_port;
grant select, insert, update, delete on inv_lag_group to svc_port;
grant select, insert, update, delete on inv_transceiver to svc_port;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_port;
