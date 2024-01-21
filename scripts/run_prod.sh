#!/bin/bash
 cd dev/projects/wadiag/

# Stop and remove existing containers
docker-compose down

# Start containers with production environment variables
docker-compose --env-file=.env.prod up --build -d
