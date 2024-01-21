#!/bin/bash

cd dev/projects/wadiag/
git reset --hard origin/master
git pull origin master

# Stop and remove existing containers
docker-compose down

# Start containers with production environment variables
docker-compose --env-file=.env.prod up --build -d
