#!/usr/bin/env bash

set -e
mvn clean package -U -Dmaven.test.skip=true
echo "Down all"
sudo docker-compose down
echo "Start Full System "
sudo docker-compose  up -d --build







