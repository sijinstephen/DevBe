#!/usr/bin/env bash
set -euo pipefail

DB_NAME="accDB"
DB_USER="accUser"

# Ensure mysql client is available
command -v mysql >/dev/null 2>&1 || { echo "mysql client not found in PATH"; exit 1; }

# Ask for the new user's password (hidden input)
read -s -p "Enter password for new MySQL user '${DB_USER}': " DB_PASS
echo

echo "You'll now be prompted for the MySQL ROOT password to apply the changes…"
mysql -uroot -p <<SQL
-- Create database (idempotent)
CREATE DATABASE IF NOT EXISTS \`${DB_NAME}\`
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

-- Create user if missing, then (re)set password to what you entered
CREATE USER IF NOT EXISTS '${DB_USER}'@'localhost' IDENTIFIED BY '${DB_PASS}';
ALTER  USER                '${DB_USER}'@'localhost' IDENTIFIED BY '${DB_PASS}';

-- Grant full access on this database
GRANT ALL PRIVILEGES ON \`${DB_NAME}\`.* TO '${DB_USER}'@'localhost';
FLUSH PRIVILEGES;
SQL

echo "✅ Done. DB '${DB_NAME}' and user '${DB_USER}' are ready."
echo "Test it with:  mysql -u ${DB_USER} -p ${DB_NAME} -e 'SELECT 1;'"
