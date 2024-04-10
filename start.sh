#!/bin/bash

echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
docker-compose pull
docker-compose up -d
