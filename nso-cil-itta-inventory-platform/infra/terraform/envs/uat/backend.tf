terraform {
  required_version = ">= 1.8.0"
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 3.116"
    }
  }
  backend "azurerm" {
    resource_group_name  = "rg-cil-tfstate"
    storage_account_name = "stcilttatfstate"
    container_name       = "tfstate"
    key                  = "itta-inventory/uat.tfstate"
  }
}

provider "azurerm" {
  features {}
  subscription_id = var.subscription_id
}
