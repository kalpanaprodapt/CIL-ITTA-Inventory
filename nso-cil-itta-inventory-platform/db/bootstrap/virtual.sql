-- Bootstrap for the Virtual and Cloud schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_virtual') then
    create role svc_virtual login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_virtual;
grant usage on schema public to svc_virtual;

-- tables are created by Flyway from inventory-virtual-service; grants applied afterwards
grant select, insert, update, delete on inv_vnf to svc_virtual;
grant select, insert, update, delete on inv_hypervisor to svc_virtual;
grant select, insert, update, delete on inv_virtual_machine to svc_virtual;
grant select, insert, update, delete on inv_cloud_account to svc_virtual;
grant select, insert, update, delete on inv_k8s_cluster to svc_virtual;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_virtual;
