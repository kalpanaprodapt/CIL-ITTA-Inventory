variable "name" { type = string }
variable "location" { type = string }
variable "resource_group_name" { type = string }
variable "subnet_id" { type = string }
variable "private_dns_zone_id" { type = string }
variable "admin_password" {
  type      = string
  sensitive = true
}
variable "sku_name" {
  type    = string
  default = "GP_Standard_D4ds_v5"
}
variable "storage_mb" {
  type    = number
  default = 262144
}
variable "backup_retention_days" {
  type    = number
  default = 14
}
variable "geo_redundant" {
  type    = bool
  default = false
}
variable "high_availability" {
  type    = bool
  default = false
}
variable "tags" {
  type    = map(string)
  default = {}
}
