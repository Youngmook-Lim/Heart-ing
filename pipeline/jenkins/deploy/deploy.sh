echo "*********************************"
echo "***DEPLOYING DOCKER CONTAINERS***"
echo "*********************************"

# docker container run
cd /var/jenkins_home/workspace/bbb-pipeline/pipeline
docker compose up -d

# delete dangling images
docker image prune -f