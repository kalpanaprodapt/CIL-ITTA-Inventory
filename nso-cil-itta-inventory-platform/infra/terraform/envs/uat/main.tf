locals {
  name = "cil-itta-uat"
  tags = {
    application = "itta-inventory"
    environment = var.environment
    owner       = "cil-platform"
    costcenter  = "cil-network"
  }
  workloads = ["inventory-geography-service", "inventory-facility-service", "inventory-device-service", "inventory-port-service", "inventory-cabling-service", "inventory-vendor-service", "inventory-software-service", "inventory-ipam-service", "inventory-switching-service", "inventory-circuit-service", "inventory-topology-service", "inventory-customer-service", "inventory-service-service", "inventory-mpls-service", "inventory-optical-service", "inventory-radio-service", "inventory-virtual-service", "inventory-discovery-service", "inventory-change-service", "inventory-assetmgmt-service", "inventory-compliance-service", "inventory-alarm-service", "inventory-capacity-service", "inventory-integration-service", "inventory-geography-api", "inventory-facility-api", "inventory-device-api", "inventory-port-api", "inventory-cabling-api", "inventory-vendor-api", "inventory-software-api", "inventory-ipam-api", "inventory-switching-api", "inventory-circuit-api", "inventory-topology-api", "inventory-customer-api", "inventory-service-api", "inventory-mpls-api", "inventory-optical-api", "inventory-radio-api", "inventory-virtual-api", "inventory-discovery-api", "inventory-change-api", "inventory-assetmgmt-api", "inventory-compliance-api", "inventory-alarm-api", "inventory-capacity-api", "inventory-integration-api", "inventory-ui"]
  secret_names = concat(
    ["itta-eventhub-connection", "itta-aad-client-secret"],
    [for d in ["geography", "facility", "device", "port", "cabling", "vendor", "software", "ipam", "switching", "circuit", "topology", "customer", "service", "mpls", "optical", "radio", "virtual", "discovery", "change", "assetmgmt", "compliance", "alarm", "capacity", "integration"] : "itta-${d}-db-password"]
  )
}

resource "azurerm_resource_group" "this" {
  name     = "rg-${local.name}"
  location = var.location
  tags     = local.tags
}

module "network" {
  source              = "../../modules/network"
  name                = local.name
  location            = var.location
  resource_group_name = azurerm_resource_group.this.name
  address_space       = var.address_space
  tags                = local.tags
}

module "monitor" {
  source              = "../../modules/monitor"
  name                = "log-${local.name}"
  location            = var.location
  resource_group_name = azurerm_resource_group.this.name
  alert_email         = var.alert_email
  tags                = local.tags
}

module "acr" {
  source              = "../../modules/acr"
  name                = "acrcilitta${var.environment}"
  location            = var.location
  resource_group_name = azurerm_resource_group.this.name
  tags                = local.tags
}

module "keyvault" {
  source              = "../../modules/keyvault"
  name                = "kv-${local.name}"
  location            = var.location
  resource_group_name = azurerm_resource_group.this.name
  allowed_subnet_ids  = [module.network.aks_subnet_id]
  secret_names        = local.secret_names
  tags                = local.tags
}

module "aks" {
  source                     = "../../modules/aks"
  cluster_name               = "aks-cil-nonprod-eus2"
  location                   = var.location
  resource_group_name        = azurerm_resource_group.this.name
  kubernetes_version         = var.kubernetes_version
  subnet_id                  = module.network.aks_subnet_id
  acr_id                     = module.acr.id
  log_analytics_workspace_id = module.monitor.workspace_id
  apps_min                   = var.apps_min
  apps_max                   = var.apps_max
  tags                       = local.tags
}

module "postgres" {
  source              = "../../modules/postgres"
  name                = "pg-${local.name}"
  location            = var.location
  resource_group_name = azurerm_resource_group.this.name
  subnet_id           = module.network.data_subnet_id
  private_dns_zone_id = azurerm_private_dns_zone.postgres.id
  admin_password      = var.postgres_admin_password
  sku_name            = var.postgres_sku
  high_availability   = var.postgres_ha
  geo_redundant       = var.postgres_ha
  tags                = local.tags
}

resource "azurerm_private_dns_zone" "postgres" {
  name                = "privatelink.postgres.database.azure.com"
  resource_group_name = azurerm_resource_group.this.name
}

module "eventhubs" {
  source              = "../../modules/eventhubs"
  name                = "evh-${local.name}"
  location            = var.location
  resource_group_name = azurerm_resource_group.this.name
  capacity            = var.eventhub_capacity
  tags                = local.tags
}

module "identity" {
  source              = "../../modules/identity"
  workloads           = local.workloads
  environment         = var.environment
  namespace           = "itta-inventory-${var.environment}"
  location            = var.location
  resource_group_name = azurerm_resource_group.this.name
  oidc_issuer_url     = module.aks.oidc_issuer_url
  key_vault_id        = module.keyvault.id
  tags                = local.tags
}
