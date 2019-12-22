#!/bin/bash
docker exec 93aac1306a29 /usr/bin/pg_dump --username=postgres > backup.sql