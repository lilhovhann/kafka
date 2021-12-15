#!/bin/bash


if [ $# -lt 1 ]
then
        echo "Usage : missing params!!"
        exit
fi


sudo docker logs --follow $1


