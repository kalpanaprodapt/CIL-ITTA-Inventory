-- Bootstrap for the Asset and Finance schema objects (executed once per environment by the db-init job)
do $$
begin
  if not exists (select 1 from pg_roles where rolname = 'svc_assetmgmt') then
    create role svc_assetmgmt login password null;
  end if;
end $$;

grant connect on database itta_inventory to svc_assetmgmt;
grant usage on schema public to svc_assetmgmt;

-- tables are created by Flyway from inventory-assetmgmt-service; grants applied afterwards
grant select, insert, update, delete on inv_asset to svc_assetmgmt;
grant select, insert, update, delete on inv_asset_tag to svc_assetmgmt;
grant select, insert, update, delete on inv_purchase_order to svc_assetmgmt;
grant select, insert, update, delete on inv_invoice to svc_assetmgmt;
grant select, insert, update, delete on inv_depreciation_schedule to svc_assetmgmt;

alter default privileges in schema public grant select, insert, update, delete on tables to svc_assetmgmt;
