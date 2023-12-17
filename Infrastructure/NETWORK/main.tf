########## VPC CONFIGURATION SECTION ###########

resource "aws_vpc" "new_vpc" {
  cidr_block       = var.vpc_cidr

  tags = {
    Name = join("-", [var.infrastucture_environment_name, "VPC"])
  }
}

########## INTERNET-GATEWAY CONFIGURATION SECTION ##########

resource "aws_internet_gateway" "internet_gateway" {
  vpc_id = aws_vpc.new_vpc.id

  tags = {
    "Name" = join("-", [var.infrastucture_environment_name, "internet-gateway"])
  }
}

########## VPC SUBNET CONFIGURATION SECTION #########

# PUBLIC SUBNET CONFIGURATION SECTION

resource "aws_subnet" "public_subnet" {
  count = length(var.public_cidrs)
  vpc_id = aws_vpc.new_vpc.id
  cidr_block = var.public_cidrs[count.index]
  map_public_ip_on_launch = true
  availability_zone = "${var.availability_zone}"[count.index]


  tags = {
    Name = join("-", [var.infrastucture_environment_name, "public-subnet-${count.index + 1}"])
/*     "kubernetes.io/role/elb" = "1"
    "kubernetes.io/cluster/ogunleye_cluster" = "owned" */

  }
}

# PRIVATE SUBNET CONFIGURATION SECTION

resource "aws_subnet" "private_subnet" {
  count = length(var.private_cidrs)
  vpc_id = aws_vpc.new_vpc.id
  cidr_block = var.private_cidrs[count.index]
  map_public_ip_on_launch = false
  availability_zone = "${var.availability_zone}"[count.index]


  tags = {
    Name = join("-", [var.infrastucture_environment_name, "private-subnet-${count.index + 1}"])
/*     "kubernetes.io/role/internal-elb" = "1"
    "kubernetes.io/cluster/ogunleye_cluster" = "owned" */
  }
}

########## NAT-GATEWAY CONFIGURATION SECTION ##########

resource "aws_eip" "aws_eip" {
  count = length(var.private_cidrs)
  domain = "vpc"
}

resource "aws_nat_gateway" "nat_gateway" {
  count = length(var.private_cidrs)
  allocation_id = aws_eip.aws_eip[count.index].id
  subnet_id     = aws_subnet.public_subnet[count.index].id

  tags = {
    Name = join("-", [var.infrastucture_environment_name, "NAT-gateway-${count.index + 1}"])
  }

  # To ensure proper ordering, it is recommended to add an explicit dependency
  # on the Internet Gateway for the VPC.
  depends_on = [aws_internet_gateway.internet_gateway]
}

########## ROUTING CONFIGURATION SECTION ##########

# ROUTE-TABLE CONFIGURATION SECTION

resource "aws_route_table" "private_route_table" {
  count = length(var.private_cidrs)
  vpc_id = aws_vpc.new_vpc.id

  tags = {
    "Name" = join("-", [var.infrastucture_environment_name, "private-route-table${count.index + 1}"])
  }
}

resource "aws_route_table" "public_route_table" {
  vpc_id = aws_vpc.new_vpc.id

  tags = {
    "Name" = join("-", [var.infrastucture_environment_name, "public-route-table"])
  }
}

# ROUTE CONFIGURATION SECTION

resource "aws_route" "private_route" {
 count = length(var.private_cidrs)
 route_table_id = aws_route_table.private_route_table.*.id[count.index]
 destination_cidr_block = var.destination_cidr
 nat_gateway_id = aws_nat_gateway.nat_gateway.*.id[count.index] 
}

resource "aws_route" "public_route" {
 route_table_id = aws_route_table.public_route_table.id
 destination_cidr_block = var.destination_cidr
 gateway_id = aws_internet_gateway.internet_gateway.id 
}

# ROUTE-TABLE ASSOCIATION CONFIGURATION SECTION

resource "aws_route_table_association" "private_subnet_association" {
  count = length(var.private_cidrs)
  subnet_id      = aws_subnet.private_subnet.*.id[count.index] #Routes all the private subnet to the NAT gateway(s) to enable fault tolerance
  route_table_id = aws_route_table.private_route_table.*.id[count.index]
}

resource "aws_route_table_association" "eks_public_subnet_association" {
  count = length(var.public_cidrs)
  subnet_id      = aws_subnet.public_subnet.*.id[count.index] #Routes all the public subnets to the internet gateway to enable fault tolerance
  route_table_id = aws_route_table.public_route_table.id
}



