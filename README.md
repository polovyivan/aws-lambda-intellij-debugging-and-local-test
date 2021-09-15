
<p align="center">
  <img width="460" height="300" src="https://miro.medium.com/max/700/1*UKVrFTa7dfperH6CcofGNQ.png">
</p>

<h1 align="center"><a href="https://aws.plainenglish.io/aws-lambda-testing-and-debugging-using-intellij-aws-sam-and-docker-f489f1d39b0d">AWS Lambda Testing and Debugging using IntelliJ, AWS SAM, and Docker</a></h1>

# Commands used to access S3 bucket locally
 ```
aws --endpoint-url=http://localhost:4566 s3 ls --profile=localstack - list buckets
 
aws --endpoint-url=http://localhost:4566 s3 ls s3://tutorial-bucket --recursive --profile=localstack - list files in a bucket
 
aws --endpoint-url=http://localhost:4566 s3 cp s3://tutorial-bucket/test-file1.txt test-file1.txt --profile=localstack - doenload file from S3 bucket
