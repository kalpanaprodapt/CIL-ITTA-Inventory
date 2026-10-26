-- Bootstrap for the Alarms and Events schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_alarm') then
    create role svc_alarm login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_alarm;
grant usage on schema public to svc_alarm;

-- tables are created by Flyway from inventory-alarm-service; grants applied afterwards
grant select, insert, update, delete on inv_alarm_definition to svc_alarm;
grant select, insert, update, delete on inv_alarm_instance to svc_alarm;
grant select, insert, update, delete on inv_event_source to svc_alarm;
grant select, insert, update, delete on inv_suppression_rule to svc_alarm;
grant select, insert, update, delete on inv_ticket_link to svc_alarm;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_alarm;
