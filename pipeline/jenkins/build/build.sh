#!/bin/bash

echo "*******************************"
echo "*******Building FRONTEND*******"
echo "*******************************"

# frontend build
cd /var/jenkins_home/workspace/hearting-pipeline-docker/frontend
npm install --legacy-peer-deps
CI=false npm run build

echo "********************************"
echo "********Building BACKEND********"
echo "********************************"

# backend build
cd /var/jenkins_home/workspace/hearting-pipeline-docker/backend
gradle clean build -x test

echo "********************************"
echo "***Building DOCKER CONTAINERS***"
echo "********************************"

# docker container build
cd /var/jenkins_home/workspace/hearting-pipeline-docker/pipeline
docker compose build \
--build-arg FRONTEND_VERSION=$FRONTEND_VERSION \
--build-arg BACKEND_SPRING_VERSION=$BACKEND_SPRING_VERSION \
--build-arg BACKEND_NODEJS_VERSION=$BACKEND_NODEJS_VERSION \
--no-cache