########## ELASTIC FILE SYSTEM CONFIGURATION SECTION ##########


resource "aws_efs_file_system" "new_efs" {
  creation_token = var.creation_token
  encrypted = var.encryption
 /*  kms_key_id = #enter ARN of KMS encryption key if the encrypted attribute is set to true. */
  performance_mode = var.performance_mode
  provisioned_throughput_in_mibps = 20
  throughput_mode = var.throughput_mode

    lifecycle_policy {
    transition_to_ia = "AFTER_30_DAYS"
  }

  tags = {
    Name = join("-", [var.infrastucture_environment_name, "efs"])
  }
}

########## ELASTIC FILE SYSTEM MOUNT TARGET CONFIGURATION SECTION ##########
