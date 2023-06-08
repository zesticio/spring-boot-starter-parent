#!/bin/bash
echo "Creating docker network"
docker network create --driver=bridge --subnet=172.1.1.0/24 --gateway=172.1.1.1 activemq
