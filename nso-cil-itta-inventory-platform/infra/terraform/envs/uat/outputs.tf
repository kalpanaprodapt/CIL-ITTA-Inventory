output "aks_oidc_issuer" { value = module.aks.oidc_issuer_url }
output "postgres_fqdn" { value = module.postgres.fqdn }
output "key_vault_uri" { value = module.keyvault.vault_uri }
output "acr_login_server" { value = module.acr.login_server }
