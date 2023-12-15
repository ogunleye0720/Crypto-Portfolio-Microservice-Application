variable "eks_cluster_role" {
  type = string
  default = "eks_cluster_role"
}
variable "service_account_role" {
  type = string
  default = "service_account_role"
}
variable "eks_cluster_name" {
  type = string
  default = "digitalbank-cluster"
}
variable "subnet_ids" {
  type = list(string)
}
variable "node_group_name" {
  type = string
  default = "digitalbank-node-group"
}
variable "infrastucture_environment_name" {
  type = string
  default = "staging-DigitalBank"
}
variable "capacity_type" {
  type = string
  default = "ON_DEMAND"
}
variable "instance_types" {
  type = list(string)
  default = ["t3.medium"]
}
variable "desired_size" {
  type = number
  default = 2
}
variable "max_size" {
  type = number
  default = 4
}
variable "min_size" {
  type = number
  default = 2
}
variable "max_unavailable" {
  type = number
  default = 1
}
