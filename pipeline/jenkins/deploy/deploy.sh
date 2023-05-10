echo "*********************************"
echo "***DEPLOYING DOCKER CONTAINERS***"
echo "*********************************"

# docker container run
# cd /var/jenkins_home/workspace/hearting-pipeline-docker/pipeline
# docker compose up -d

echo "**********************************"
echo "*DEPLOYING KUBERNETES DEPLOYMENTS*"
echo "**********************************"

cd ${WORKSPACE}/pipeline/jenkins/deploy/k8s

for file in hearting-back.yml hearting-front.yml hearting-websocket.yml; do
  echo "Before sed:"
  cat $file
  sed -i "s/__PRODUCTION_VERSION__/${PRODUCTION_VERSION}/g" $file
  echo "After sed:"
  cat $file
done

echo k8
kubectl --kubeconfig=/var/jenkins_home/kubeconfig.yml apply -f .

# delete dangling images
docker image prune -f
