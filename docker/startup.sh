#!/bin/bash
set -e
# Put the script to copy across definitions here
echo "Creating user 'music_library' using psql"
psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c "CREATE USER music_library WITH SUPERUSER PASSWORD 'music_library';"

echo "Creating DB"

cat /fixtures/db_creation/001_creation.sql | psql -Umusic_library -d postgres
cat /fixtures/db_creation/002_creation.sql | psql -Upml_java -d pml
cat /fixtures/table_creation/*.sql | psql -Upml_java -d pml
