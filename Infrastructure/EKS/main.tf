########## EKS CLUSTER CONFIGURATION SECTION ##########

resource "aws_eks_cluster" "new_eks_cluster" {
  name     = var.eks_cluster_name
  role_arn = aws_iam_role.eks_iam_role.arn 

  vpc_config {
    subnet_ids = var.subnet_ids
  }

  # Ensure that IAM Role permissions are created before and deleted after EKS Cluster handling.
  # Otherwise, EKS will not be able to properly delete EKS managed EC2 infrastructure such as Security Groups.
  depends_on = [
    aws_iam_role_policy_attachment.AmazonEKSClusterPolicy,
    aws_iam_role_policy_attachment.AmazonEKSVPCResourceController,
  ]

  tags = {
    "Name" = join("-", [var.infrastucture_environment_name, "EKS-cluster"])
  }
}

########## EKS NODE GROUP ##########

resource "aws_eks_node_group" "new_eks_node_group" {
  cluster_name    = aws_eks_cluster.new_eks_cluster.name 
  node_group_name = var.node_group_name
  node_role_arn   = aws_iam_role.eks_nodes_iam.arn
  subnet_ids      = var.subnet_ids
  capacity_type  = upper(var.capacity_type)
  instance_types = var.instance_types

  scaling_config {
    desired_size = var.desired_size
    max_size     = var.max_size
    min_size     = var.min_size
  }

  update_config {
    max_unavailable = var.max_unavailable
  }

  # Ensure that IAM Role permissions are created before and deleted after EKS Node Group handling.
  # Otherwise, EKS will not be able to properly delete EC2 Instances and Elastic Network Interfaces.
  depends_on = [
    aws_iam_role_policy_attachment.AmazonEKSWorkerNodePolicy,
    aws_iam_role_policy_attachment.AmazonEKS_CNI_Policy,
    aws_iam_role_policy_attachment.AmazonEC2ContainerRegistryReadOnly,
  ]

  tags = {
    "Name" = join("-", [var.infrastucture_environment_name, "EKS-worker-nodes"])
  }
}