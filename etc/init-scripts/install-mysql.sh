#!/bin/bash

if [ $# -ne 1 ]; then
  echo "Usage: $0 <MYSQL_ROOT_PASSWORD>"
  exit 1
fi

MYSQL_ROOT_PASSWORD=$1

docker run --name mysql \
        -v /home/ubuntu/mysql:/var/lib/mysql \
        -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} \
        -d -p 3306:3306 mysql:8.0.30

for i in {1..6}; do
  # Check if the MySQL server is ready
  if docker exec mysql mysqladmin -u root -p${MYSQL_ROOT_PASSWORD} status > /dev/null 2>&1; then
    echo "MySQL server is ready."
    break
  else
    echo "MySQL server is not ready. Retrying in 5 seconds..."
    sleep 5
  fi
done