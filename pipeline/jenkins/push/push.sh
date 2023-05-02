#!/bin/bash

echo "*******************************"
echo "*********Pushing Image*********"
echo "*******************************"

IMAGE_FRONTEND="hearting-frontend"
IMAGE_SPRING_BACKEND="hearting-backend"
IMAGE_NODEJS_BACKEND="hearting-websocket"

echo "*********Docker login*********"

docker login -u youngmookk -p $PASS

docker tag $IMAGE_FRONTEND:$FRONTEND_VERSION youngmookk/$IMAGE_FRONTEND:$FRONTEND_VERSION
docker tag $IMAGE_FRONTEND:$FRONTEND_VERSION youngmookk/$IMAGE_FRONTEND:latest

echo "*********Docker tag*********"

docker tag $IMAGE_SPRING_BACKEND:$BACKEND_SPRING_VERSION youngmookk/$IMAGE_SPRING_BACKEND:$BACKEND_SPRING_VERSION
docker tag $IMAGE_SPRING_BACKEND:$BACKEND_SPRING_VERSION youngmookk/$IMAGE_SPRING_BACKEND:latest

docker tag $IMAGE_NODEJS_BACKEND:$BACKEND_NODEJS_VERSION youngmookk/$IMAGE_NODEJS_BACKEND:$BACKEND_NODEJS_VERSION
docker tag $IMAGE_NODEJS_BACKEND:$BACKEND_NODEJS_VERSION youngmookk/$IMAGE_NODEJS_BACKEND:latest

echo "*********Docker push*********"

docker push youngmookk/$IMAGE_SPRING_BACKEND:$BACKEND_SPRING_VERSION
docker push youngmookk/$IMAGE_SPRING_BACKEND:latest

docker push youngmookk/$IMAGE_FRONTEND:$FRONTEND_VERSION
docker push youngmookk/$IMAGE_FRONTEND:latest

docker push youngmookk/$IMAGE_NODEJS_BACKEND:$BACKEND_NODEJS_VERSION
docker push youngmookk/$IMAGE_NODEJS_BACKEND:latest