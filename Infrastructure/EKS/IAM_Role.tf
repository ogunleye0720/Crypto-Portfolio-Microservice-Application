########## IAM ROLE FOR EKS CLUSTER ##########

data "aws_iam_policy_document" "assume_role" {
  statement {
    effect = "Allow"

    principals {
      type        = "Service"
      identifiers = ["eks.amazonaws.com"]
    }

    actions = ["sts:AssumeRole"]
  }
}

resource "aws_iam_role" "eks_iam_role" {
  name               = var.eks_cluster_role
  assume_role_policy = data.aws_iam_policy_document.assume_role.json
}

resource "aws_iam_role_policy_attachment" "AmazonEKSClusterPolicy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"
  role       = aws_iam_role.eks_iam_role.name
}

resource "aws_iam_role_policy_attachment" "AmazonEKSVPCResourceController" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSVPCResourceController"
  role       = aws_iam_role.eks_iam_role.name
}

########## IAM ROLE FOR KUBERNETES SERVICE ACCOUNTS ##########

data "tls_certificate" "cluster_certificate" {
  url = aws_eks_cluster.new_eks_cluster.identity[0].oidc[0].issuer
}

resource "aws_iam_openid_connect_provider" "OID_provider" {
  client_id_list  = ["sts.amazonaws.com"]
  thumbprint_list = [data.tls_certificate.cluster_certificate.certificates[0].sha1_fingerprint]
  url             = data.tls_certificate.cluster_certificate.url
}

data "aws_iam_policy_document" "example_assume_role_policy" {
  statement {
    actions = ["sts:AssumeRoleWithWebIdentity"]
    effect  = "Allow"

    condition {
      test     = "StringEquals"
      variable = "${replace(aws_iam_openid_connect_provider.OID_provider.url, "https://", "")}:sub"
      values   = ["system:serviceaccount:kube-system:aws-node"]
    }

    principals {
      identifiers = [aws_iam_openid_connect_provider.OID_provider.arn]
      type        = "Federated"
    }
  }
}

/* resource "aws_iam_role" "service_account_role" {
  assume_role_policy = data.aws_iam_policy_document.example_assume_role_policy.json
  name               = var.service_account_role
} */
resource "aws_iam_role" "efs_service_account_role" {
  assume_role_policy = data.aws_iam_policy_document.example_assume_role_policy.json
  name               = var.efs_service_account_role
}

########## IAM POLICY AND ROLE ATTACHMENT FOR EFS ##########

resource "aws_iam_role_policy_attachment" "AmazonEKS_EFS_CSI_DriverRole" {
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonEFSCSIDriverPolicy"
  role       = aws_iam_role.efs_service_account_role.name 
}


 # AWS EFS POLICY
 # This would provide a fine-grain policy, allowing user define desired set of permissions.
 # Remove comment and attach to AmazonEKS_EFS_CSI_DriverRole policy_arn attribute e.g policy_arn = aws_iam_policy.efs_policy.arn 
 
/*  resource "aws_iam_policy" "efs_policy" {
  name        = "test_policy"
  path        = "/"
  description = "My test policy"

  # Terraform's "jsonencode" function converts a
  # Terraform expression result to valid JSON syntax.
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = [
          "elasticfilesystem:DescribeAccessPoints",
          "elasticfilesystem:DescribeFileSystems",
          "elasticfilesystem:DescribeMountTargets",
          "ec2:DescribeAvailabilityZones"
        ]
        Effect   = "Allow"
        Resource = "*"
      },
      {
        Action = [
          "elasticfilesystem:CreateAccessPoint",
        ]
        Effect   = "Allow"
        Resource = "*"
      },
      {
        Action = [
          "elasticfilesystem:TagResource",
        ]
        Effect   = "Allow"
        Resource = "*"
      },
      {
        Action = [
          "elasticfilesystem:DeleteAccessPoint",
        ]
        Effect   = "Allow"
        Resource = "*"
      },
    ]
  })
} */

########## EKS WORKER NODES IAM ROLES ##########

resource "aws_iam_role" "eks_nodes_iam" {
  name = "eks-node-group-example"

  assume_role_policy = jsonencode({
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = {
        Service = "ec2.amazonaws.com"
      }
    }]
    Version = "2012-10-17"
  })
}

resource "aws_iam_role_policy_attachment" "AmazonEKSWorkerNodePolicy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSWorkerNodePolicy"
  role       = aws_iam_role.eks_nodes_iam.name 
}

resource "aws_iam_role_policy_attachment" "AmazonEKS_CNI_Policy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKS_CNI_Policy"
  role       = aws_iam_role.eks_nodes_iam.name 
}

resource "aws_iam_role_policy_attachment" "AmazonEC2ContainerRegistryReadOnly" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
  role       = aws_iam_role.eks_nodes_iam.name 
}

