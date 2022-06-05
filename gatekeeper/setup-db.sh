#!/bin/bash

cd wealth-auth-db
docker build -t wealth-auth-db -f Dockerfile .
cd ..
mkdir -p var/postgres/datadir
docker-compose -f docker-compose.yml up -d db