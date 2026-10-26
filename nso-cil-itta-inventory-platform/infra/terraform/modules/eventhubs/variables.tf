variable "name" { type = string }
variable "location" { type = string }
variable "resource_group_name" { type = string }
variable "sku" {
  type    = string
  default = "Standard"
}
variable "capacity" {
  type    = number
  default = 2
}
variable "partitions" {
  type    = number
  default = 4
}
variable "retention_days" {
  type    = number
  default = 3
}
variable "tags" {
  type    = map(string)
  default = {}
}
