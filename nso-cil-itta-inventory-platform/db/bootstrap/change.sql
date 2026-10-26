-- Bootstrap for the Change and Work Management schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_change') then
    create role svc_change login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_change;
grant usage on schema public to svc_change;

-- tables are created by Flyway from inventory-change-service; grants applied afterwards
grant select, insert, update, delete on inv_change_request to svc_change;
grant select, insert, update, delete on inv_maintenance_window to svc_change;
grant select, insert, update, delete on inv_work_order to svc_change;
grant select, insert, update, delete on inv_field_task to svc_change;
grant select, insert, update, delete on inv_technician to svc_change;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_change;
