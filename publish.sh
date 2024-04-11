#!/bin/bash
./mvnw --B --file pom.xml --update-snapshots clean package
docker build . -t kerniussur/hivenote-backend --platform linux/amd64
docker push kerniussur/hivenote-backend
