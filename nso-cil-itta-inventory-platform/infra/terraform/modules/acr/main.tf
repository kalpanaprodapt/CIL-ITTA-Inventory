resource "azurerm_container_registry" "this" {
  name                          = var.name
  resource_group_name           = var.resource_group_name
  location                      = var.location
  sku                           = "Premium"
  admin_enabled                 = false
  public_network_access_enabled = false
  zone_redundancy_enabled       = true

  retention_policy {
    days    = 30
    enabled = true
  }
  trust_policy {
    enabled = true
  }
  tags = var.tags
}
