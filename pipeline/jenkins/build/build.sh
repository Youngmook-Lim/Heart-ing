#!/bin/bash

echo "*******************************"
echo "*******Building FRONTEND*******"
echo "*******************************"

# frontend build
cd /var/jenkins_home/workspace/hearting-pipeline-docker/frontend
npm install --legacy-peer-deps
npm run build

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
docker compose build --no-cache