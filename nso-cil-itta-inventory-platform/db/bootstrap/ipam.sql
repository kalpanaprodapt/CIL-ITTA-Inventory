-- Bootstrap for the IP Management schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_ipam') then
    create role svc_ipam login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_ipam;
grant usage on schema public to svc_ipam;

-- tables are created by Flyway from inventory-ipam-service; grants applied afterwards
grant select, insert, update, delete on inv_ip_subnet to svc_ipam;
grant select, insert, update, delete on inv_ip_address to svc_ipam;
grant select, insert, update, delete on inv_ip_pool to svc_ipam;
grant select, insert, update, delete on inv_vrf_instance to svc_ipam;
grant select, insert, update, delete on inv_dns_record to svc_ipam;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_ipam;
