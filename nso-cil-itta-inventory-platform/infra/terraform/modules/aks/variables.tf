variable "cluster_name" { type = string }
variable "location" { type = string }
variable "resource_group_name" { type = string }
variable "kubernetes_version" { type = string }
variable "sku_tier" {
  type    = string
  default = "Standard"
}
variable "subnet_id" { type = string }
variable "acr_id" { type = string }
variable "log_analytics_workspace_id" { type = string }
variable "system_vm_size" {
  type    = string
  default = "Standard_D4s_v5"
}
variable "system_min" {
  type    = number
  default = 3
}
variable "system_max" {
  type    = number
  default = 5
}
variable "apps_vm_size" {
  type    = string
  default = "Standard_D8s_v5"
}
variable "apps_min" {
  type    = number
  default = 3
}
variable "apps_max" {
  type    = number
  default = 12
}
variable "tags" {
  type    = map(string)
  default = {}
}
