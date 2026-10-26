data "azurerm_client_config" "current" {}

resource "azurerm_key_vault" "this" {
  name                       = var.name
  location                   = var.location
  resource_group_name        = var.resource_group_name
  tenant_id                  = data.azurerm_client_config.current.tenant_id
  sku_name                   = "standard"
  enable_rbac_authorization  = true
  purge_protection_enabled   = true
  soft_delete_retention_days = 30

  network_acls {
    default_action             = "Deny"
    bypass                     = "AzureServices"
    virtual_network_subnet_ids = var.allowed_subnet_ids
  }
  tags = var.tags
}

resource "azurerm_key_vault_secret" "placeholder" {
  for_each     = toset(var.secret_names)
  name         = each.value
  value        = "REPLACE_ME"
  key_vault_id = azurerm_key_vault.this.id
  lifecycle {
    ignore_changes = [value]
  }
}
