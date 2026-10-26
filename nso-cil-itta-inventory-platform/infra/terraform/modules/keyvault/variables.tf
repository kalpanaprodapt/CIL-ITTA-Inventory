variable "name" { type = string }
variable "location" { type = string }
variable "resource_group_name" { type = string }
variable "allowed_subnet_ids" { type = list(string) }
variable "secret_names" { type = list(string) }
variable "tags" {
  type    = map(string)
  default = {}
}
