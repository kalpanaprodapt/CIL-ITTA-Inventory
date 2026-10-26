-- Bootstrap for the L2 and L3 Routing schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_switching') then
    create role svc_switching login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_switching;
grant usage on schema public to svc_switching;

-- tables are created by Flyway from inventory-switching-service; grants applied afterwards
grant select, insert, update, delete on inv_vlan to svc_switching;
grant select, insert, update, delete on inv_vlan_pool to svc_switching;
grant select, insert, update, delete on inv_route_target to svc_switching;
grant select, insert, update, delete on inv_bgp_session to svc_switching;
grant select, insert, update, delete on inv_ospf_area to svc_switching;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_switching;
