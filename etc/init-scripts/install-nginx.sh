# 1. Nginx 설치
sudo apt-get install nginx
nginx -v

# 2. Let's Encrypt 설치 및 SSL 발급
sudo apt-get install letsencrypt
sudo systemctl stop nginx
sudo letsencrypt certonly --standalone -d $1

# 3. Nginx 설정파일 배치
sudo cp nginx.conf /etc/nginx/sites-available
sudo ln -s /etc/nginx/sites-available/configure /etc/nginx/sites-enabled/configure
sudo nginx -t # ok 시 성공
sudo systemctl restart nginx
