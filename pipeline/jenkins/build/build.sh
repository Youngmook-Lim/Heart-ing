#!/bin/bash

echo "*******************************"
echo "*******Building Frontend*******"
echo "*******************************"

# frontend build
cd ${WORKSPACE}/frontend
npm install --legacy-peer-deps
CI=false npm run build

echo "********************************"
echo "********Building Backend********"
echo "********************************"

# backend build
cd /var/jenkins_home/workspace/hearting-pipeline-docker/backend
gradle clean build -x test

echo "********************************"
echo "***Building Container Images***"
echo "********************************"

# docker container build
cd /var/jenkins_home/workspace/hearting-pipeline-docker/pipeline
docker compose build \
--build-arg PRODUCTION_VERSION=$PRODUCTION_VERSION \
--no-cache