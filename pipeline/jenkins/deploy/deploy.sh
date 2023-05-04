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

for file in hearting-back.yml hearting-front.yml hearting-websocket.yml; do
  sed -i "s/__PRODUCTION_VERSION__/${PRODUCTION_VERSION}/g" $file
done

kubectl --kubeconfig=/var/jenkins_home/kubeconfig.yml apply -f .

# delete dangling images
docker image prune -f