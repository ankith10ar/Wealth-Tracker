#!/bin/bash

cd wealth-auth-redis
docker build -t wealth-auth-redis .
cd ..
docker-compose -f docker-compose.yml up -d redis