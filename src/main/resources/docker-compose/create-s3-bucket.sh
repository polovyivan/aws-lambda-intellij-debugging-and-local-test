#!/bin/bash
aws configure set aws_access_key_id access_key --profile=localstack
aws configure set aws_secret_access_key secret_key --profile=localstack
aws configure set region us-east-1 --profile=localstack
aws --endpoint-url=http://localhost:4566 s3 mb s3://tutorial-bucket --profile=localstack
aws --endpoint-url=http://localhost:4566 s3 ls --profile=localstack