#!/bin/bash
docker exec postgres_blog /usr/bin/pg_dump --username=postgres > backup.sql