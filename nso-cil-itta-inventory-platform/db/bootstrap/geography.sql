-- Bootstrap for the Geography schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_geography') then
    create role svc_geography login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_geography;
grant usage on schema public to svc_geography;

-- tables are created by Flyway from inventory-geography-service; grants applied afterwards
grant select, insert, update, delete on inv_country to svc_geography;
grant select, insert, update, delete on inv_region to svc_geography;
grant select, insert, update, delete on inv_city to svc_geography;
grant select, insert, update, delete on inv_site to svc_geography;
grant select, insert, update, delete on inv_building to svc_geography;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_geography;
