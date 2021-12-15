#!/usr/bin/env bash
sudo docker rm -f $(docker ps -a -q)
#docker rmi $(docker images -a -q)
sudo docker rmi $(docker images | grep "^<none>" | awk "{print $3}")
sudo docker rmi $(docker images | grep "axele" | awk "{print $3}")

sudo docker images

# clean all target folders from PC
#sudo find . -type d -name target -prune -exec rm -r {} +
