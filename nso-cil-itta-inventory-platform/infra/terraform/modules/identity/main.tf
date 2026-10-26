resource "azurerm_user_assigned_identity" "workload" {
  for_each            = toset(var.workloads)
  name                = "id-${each.value}-${var.environment}"
  location            = var.location
  resource_group_name = var.resource_group_name
  tags                = var.tags
}

resource "azurerm_federated_identity_credential" "workload" {
  for_each            = toset(var.workloads)
  name                = "fic-${each.value}-${var.environment}"
  resource_group_name = var.resource_group_name
  parent_id           = azurerm_user_assigned_identity.workload[each.value].id
  audience            = ["api://AzureADTokenExchange"]
  issuer              = var.oidc_issuer_url
  subject             = "system:serviceaccount:${var.namespace}:${each.value}"
}

resource "azurerm_role_assignment" "kv_secrets_user" {
  for_each             = toset(var.workloads)
  scope                = var.key_vault_id
  role_definition_name = "Key Vault Secrets User"
  principal_id         = azurerm_user_assigned_identity.workload[each.value].principal_id
}
