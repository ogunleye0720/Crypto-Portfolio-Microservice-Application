terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

# Configure the AWS Provider
provider "aws" {
  region = "us-east-1"
}
# Hashicorp Vault Provider
provider "vault" {
  address = "http://127.0.0.1:8200"
  token = "hvs.dpRpCU2HjyXMQRpZ7w3hwJOG"
}

# NETWORK MODULE SECTION

module "Network" {
  source = "./NETWORK"
}

# EFS MODULE SECTION
module "EFS" {
source = "./EFS"
target_subnet = module.Network.private_subnet_ids
}

# SECRET_MANAGER MODULE SECTION
module "SECRET_MANAGER" {
  source = "./SECRETE_STORE"
}