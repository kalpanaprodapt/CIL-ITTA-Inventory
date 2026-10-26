variable "subscription_id" { type = string }
variable "location" { type = string }
variable "environment" { type = string }
variable "address_space" { type = string }
variable "kubernetes_version" { type = string }
variable "alert_email" { type = string }
variable "postgres_sku" { type = string }
variable "postgres_ha" { type = bool }
variable "postgres_admin_password" {
  type      = string
  sensitive = true
}
variable "eventhub_capacity" { type = number }
variable "apps_min" { type = number }
variable "apps_max" { type = number }
