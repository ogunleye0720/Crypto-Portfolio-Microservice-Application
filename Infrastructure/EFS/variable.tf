variable "target_subnet" {
    type = list(any)
    description = "list of target subnets to mount the efs target"
}

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
  type = string
  default = "provisioned"
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
  type = string 
  default = "20"
}
variable "number_of_target" {
  type = number
  default = 2
  description = "The number of tagets determines the number of subnets to attach efs_mount_target" #It must match the number of subnets to be used !!!
}
variable "backup_policy_status" {
  type = string
  default = "enabled"
}