variable "vpc_cidr" {
  type = string
  default = "10.0.0.0/16"
}

variable "availability_zone" {
  type = list(string)
  default = ["us-east-1a", "us-east-1b", "us-east-1c", "us-east-1d", "us-east-1e", "us-east-1f"]
  description = "list of availability zones where infrastucture will be created"
}

variable "private_cidrs" {
  type = list(any)
  default = ["10.0.3.0/24","10.0.4.0/24"]
}

variable "public_cidrs" {
  type = list(any)
  default = [ "10.0.1.0/24","10.0.2.0/24"]
}

variable "destination_cidr" {
  type = string
  default = "0.0.0.0/0"
}

variable "infrastucture_environment_name" {
  type = string
  default = "staging-DigitalBank"
}