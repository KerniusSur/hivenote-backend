#!/bin/bash
./mvnw clean package -DskipTests
docker build . -t kerniussur/hivenote-backend --platform linux/amd64
docker push kerniussur/hivenote-backend
