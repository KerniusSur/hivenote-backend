#!/bin/bash
./mvnw clean package -DskipTests
docker build . -t kerniussur/hivenote-backend
docker push kerniussur/hivenote-backend
