########## ELASTIC FILE SYSTEM CONFIGURATION SECTION ##########

resource "aws_efs_file_system" "new_efs" {
  creation_token = var.creation_token
  throughput_mode = var.throughput_mode
  encrypted = var.encryption
 /*  kms_key_id = #enter ARN of KMS encryption key if the encrypted attribute is set to true. */
  performance_mode = var.performance_mode
  provisioned_throughput_in_mibps = var.provisioned_throughput_in_mibps

    lifecycle_policy {
    transition_to_ia = "AFTER_30_DAYS"
  }

  tags = {
    Name = join("-", [var.infrastucture_environment_name, "efs"])
  }
}

########## ELASTIC FILE SYSTEM MOUNT TARGET CONFIGURATION SECTION ##########

resource "aws_efs_mount_target" "efs_target" {
  count = var.number_of_target > 0 ? var.number_of_target : 0
  file_system_id = aws_efs_file_system.new_efs.id 
  subnet_id      = var.target_subnet[count.index]
}

########## EFS BACKUP POLICY ##########

resource "aws_efs_backup_policy" "efs_backup_policy" {
  file_system_id = aws_efs_file_system.new_efs.id 

  backup_policy {
    status = upper(var.backup_policy_status) 
  }
}