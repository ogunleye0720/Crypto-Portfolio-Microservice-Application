terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

# JENKINS MODULE

module "Jenkins" {
  source = "https://github.com/ogunleye0720/Continuous-Deployment-Of-SockShop-Microservice-Application/tree/master/Jenkins_Server"
}