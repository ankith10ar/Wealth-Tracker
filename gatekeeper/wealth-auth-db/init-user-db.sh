#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER dabbuapp WITH PASSWORD 'Xu5209Yuli';
    CREATE DATABASE wealthauth;
    GRANT ALL PRIVILEGES ON DATABASE wealthauth TO dabbuapp;
EOSQL