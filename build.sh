#!/bin/bash
./mvnw clean package -DskipTests
docker build . -t 992382369361.dkr.ecr.eu-central-1.amazonaws.com/hivenote-frontend --platform linux/amd64
