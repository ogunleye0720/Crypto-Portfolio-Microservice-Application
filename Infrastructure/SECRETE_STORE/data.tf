data "vault_generic_secret" "digitalbank_vault" {
  path = var.vault_generic_secret_path
}