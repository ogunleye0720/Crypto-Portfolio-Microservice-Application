########## SECRETS MANAGER CONFIGURATION SECTION ##########

resource "aws_secretsmanager_secret" "new_sectrets_manager" {
  name = var.secretsmanager_name
}

########## PASSING SECRETS TO SECRETS MANAGER USING HASHICORP VAULT ##########

resource "aws_secretsmanager_secret_version" "example" {
secret_id     = aws_secretsmanager_secret.new_sectrets_manager.id 
secret_string = jsonencode({
IO_DIGISIC_CREDIT_USERNAME = "${data.vault_generic_secret.digitalbank_vault.data["IO_DIGISIC_CREDIT_USERNAME"]}",
SPRING_ARTEMIS_PASSWORD = "${data.vault_generic_secret.digitalbank_vault.data["SPRING_ARTEMIS_PASSWORD"]}",
SPRING_ARTEMIS_USER = "${data.vault_generic_secret.digitalbank_vault.data["SPRING_ARTEMIS_USER"]}",
})
}