-- Bootstrap for the Software and Licensing schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_software') then
    create role svc_software login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_software;
grant usage on schema public to svc_software;

-- tables are created by Flyway from inventory-software-service; grants applied afterwards
grant select, insert, update, delete on inv_software_image to svc_software;
grant select, insert, update, delete on inv_software_version to svc_software;
grant select, insert, update, delete on inv_patch to svc_software;
grant select, insert, update, delete on inv_license_key to svc_software;
grant select, insert, update, delete on inv_license_pool to svc_software;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_software;
