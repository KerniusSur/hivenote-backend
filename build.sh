#!/bin/bash
./mvnw clean package -DskipTests
docker build . -t kernius/hivenote-backend