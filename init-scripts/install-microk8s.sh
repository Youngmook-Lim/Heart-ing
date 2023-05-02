sudo snap install microk8s --classic
sudo usermod -a -G microk8s ubuntu
sudo chown -R ubuntu ~/.kube
newgrp microk8s
echo "alias kubectl='microk8s.kubectl'" >> ~/.bashrc
microk8s enable dns
