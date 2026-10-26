-- Bootstrap for the Compliance and Audit schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_compliance') then
    create role svc_compliance login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_compliance;
grant usage on schema public to svc_compliance;

-- tables are created by Flyway from inventory-compliance-service; grants applied afterwards
grant select, insert, update, delete on inv_compliance_rule to svc_compliance;
grant select, insert, update, delete on inv_compliance_check to svc_compliance;
grant select, insert, update, delete on inv_audit_record to svc_compliance;
grant select, insert, update, delete on inv_compliance_exception to svc_compliance;
grant select, insert, update, delete on inv_policy_document to svc_compliance;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_compliance;
