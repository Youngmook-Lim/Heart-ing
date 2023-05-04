echo "*********************************"
echo "***DEPLOYING DOCKER CONTAINERS***"
echo "*********************************"

# docker container run
# cd /var/jenkins_home/workspace/hearting-pipeline-docker/pipeline
# docker compose up -d

echo "**********************************"
echo "*DEPLOYING KUBERNETES DEPLOYMENTS*"
echo "**********************************"

cd /var/jenkins_home/workspace/hearting-pipeline-docker/pipeline/jenkins/deploy/k8s
kubectl --kubeconfig=/var/jenkins_home/kubeconfig.yml apply -f .

# delete dangling images
docker image prune -f