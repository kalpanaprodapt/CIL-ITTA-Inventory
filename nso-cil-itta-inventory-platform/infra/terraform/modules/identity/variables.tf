variable "workloads" { type = list(string) }
variable "environment" { type = string }
variable "namespace" { type = string }
variable "location" { type = string }
variable "resource_group_name" { type = string }
variable "oidc_issuer_url" { type = string }
variable "key_vault_id" { type = string }
variable "tags" {
  type    = map(string)
  default = {}
}
