output "vpc_id" {
  value = aws_vpc.new_vpc.id
}

output "private_subnet_ids" {
  value = aws_subnet.private_subnet.*.id
}
