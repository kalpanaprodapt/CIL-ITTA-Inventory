-- Bootstrap for the Circuits schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_circuit') then
    create role svc_circuit login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_circuit;
grant usage on schema public to svc_circuit;

-- tables are created by Flyway from inventory-circuit-service; grants applied afterwards
grant select, insert, update, delete on inv_circuit to svc_circuit;
grant select, insert, update, delete on inv_circuit_segment to svc_circuit;
grant select, insert, update, delete on inv_circuit_provider to svc_circuit;
grant select, insert, update, delete on inv_circuit_termination to svc_circuit;
grant select, insert, update, delete on inv_circuit_order to svc_circuit;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_circuit;
