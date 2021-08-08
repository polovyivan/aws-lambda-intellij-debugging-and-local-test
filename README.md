# aws-lambda-intellij-debugging-and-local-test


 ```
aws --endpoint-url=http://localhost:4566 s3 ls - list buckets
 
aws --endpoint-url=http://localhost:4566 s3 ls s3://tutorial-bucket --recursive - list files in a bucket
 
aws --endpoint-url=http://localhost:4566 s3 cp s3://tutorial-bucket/test-file.txt src/main/resources/test-file-local.txt - copy a file from S3 to a resources folder