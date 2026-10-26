-- Bootstrap for the Topology and Links schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_topology') then
    create role svc_topology login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_topology;
grant usage on schema public to svc_topology;

-- tables are created by Flyway from inventory-topology-service; grants applied afterwards
grant select, insert, update, delete on inv_link to svc_topology;
grant select, insert, update, delete on inv_topology_node to svc_topology;
grant select, insert, update, delete on inv_topology_edge to svc_topology;
grant select, insert, update, delete on inv_topology_view to svc_topology;
grant select, insert, update, delete on inv_path_trace to svc_topology;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_topology;
