-- Bootstrap for the Optical schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_optical') then
    create role svc_optical login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_optical;
grant usage on schema public to svc_optical;

-- tables are created by Flyway from inventory-optical-service; grants applied afterwards
grant select, insert, update, delete on inv_optical_channel to svc_optical;
grant select, insert, update, delete on inv_wdm_system to svc_optical;
grant select, insert, update, delete on inv_otn_link to svc_optical;
grant select, insert, update, delete on inv_amplifier to svc_optical;
grant select, insert, update, delete on inv_roadm to svc_optical;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_optical;
