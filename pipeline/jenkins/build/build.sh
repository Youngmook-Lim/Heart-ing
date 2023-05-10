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
cd ${WORKSPACE}/backend
gradle clean build -x test

echo "********************************"
echo "***Building Container Images***"
echo "********************************"

# docker container build
cd ${WORKSPACE}/pipeline
docker compose build \
--build-arg PRODUCTION_VERSION=$PRODUCTION_VERSION \
--no-cache