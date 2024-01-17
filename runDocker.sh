#!/bin/bash

if [ -n "$1" ]; then
  tag=$1
else
  echo "배포하고자 하는 version parameter 가 필요합니다."
  exit 1
fi

dockerImage="kjuiop/real-estate-admin-web:$tag"
echo $dockerImage

docker stop real-estate
docker rm real-estate
docker pull $dockerImage

docker run -d \
        --name real-estate \
        -p 80:8080 \
        --restart always \
        $dockerImage