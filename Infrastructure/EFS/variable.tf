variable "infrastucture_environment_name" {
  type = string
  default = "staging-DigitalBank"
}

variable "creation_token" {
  type = string
  default = "MyEfsToken"
  description = "A unique name (a maximum of 64 characters are allowed) used as reference when creating the Elastic File System to ensure idempotent file system creation"
}

variable "throughput_mode" {
  type = any
  default = provisioned
}

variable "performance_mode" {
  type = string
  default = "generalPurpose"
  description = "The file system performance mode."
}

variable "encryption" {
  type = bool
  default = false
}

variable "provisioned_throughput_in_mibps" {
  type = number
  default = 20
}