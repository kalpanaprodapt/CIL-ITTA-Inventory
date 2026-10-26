-- Bootstrap for the Customers and SLA schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_customer') then
    create role svc_customer login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_customer;
grant usage on schema public to svc_customer;

-- tables are created by Flyway from inventory-customer-service; grants applied afterwards
grant select, insert, update, delete on inv_customer to svc_customer;
grant select, insert, update, delete on inv_customer_account to svc_customer;
grant select, insert, update, delete on inv_customer_contact to svc_customer;
grant select, insert, update, delete on inv_contract to svc_customer;
grant select, insert, update, delete on inv_sla_profile to svc_customer;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_customer;
