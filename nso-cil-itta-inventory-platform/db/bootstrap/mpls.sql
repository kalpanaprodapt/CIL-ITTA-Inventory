-- Bootstrap for the MPLS and VPN schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_mpls') then
    create role svc_mpls login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_mpls;
grant usage on schema public to svc_mpls;

-- tables are created by Flyway from inventory-mpls-service; grants applied afterwards
grant select, insert, update, delete on inv_mpls_lsp to svc_mpls;
grant select, insert, update, delete on inv_vpn_service to svc_mpls;
grant select, insert, update, delete on inv_pseudo_wire to svc_mpls;
grant select, insert, update, delete on inv_tunnel_endpoint to svc_mpls;
grant select, insert, update, delete on inv_qos_policy to svc_mpls;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_mpls;
