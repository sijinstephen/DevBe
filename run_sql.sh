#!/usr/bin/env bash
# run_sql.sh - Execute a set of SQL files in a sensible order and log results.
# Usage:
#   ./run_sql.sh -u USER -d DBNAME -D /path/to/sql_dir [options]
#   ./run_sql.sh -u USER -d DBNAME -z /path/to/sql_zip [options]
#
# Options:
#   -u  MySQL user (required)
#   -d  Database name to use after 00_database.sql (required for post files)
#   -D  Directory containing split .sql files
#   -z  Zip file containing split .sql files (will be extracted to a temp dir)
#   -h  Host (default: localhost)
#   -P  Port (default: 3306)
#   -c  Path to mysql client binary (default: mysql in PATH)
#   -l  Logs directory (default: ./logs)
#   -y  Do not prompt for password (use MYSQL_PWD env var if set)
#
# The script runs:
#   00_database.sql (no database selected)
#   01_*.sql        (optional)
#   10_*.sql, 15_*.sql, 20_*.sql (sorted)
#   99_post.sql     (optional)
# and then any remaining *.sql not yet run, in sorted order.
#
set -u
set -o pipefail

print_usage() {
  grep '^# ' "$0" | sed 's/^# //'
}

USER_NAME="accUser"
DB_NAME="accDB"
HOST="localhost"
PORT="3306"
SQL_DIR="/Users/sijin-mini/DevBe/sql"
SQL_ZIP=""
MYSQL_BIN="mysql"
LOG_DIR="./logs"
PROMPT_PASS="yes"

while getopts "u:d:h:P:D:z:c:l:y" opt; do
  case "$opt" in
    u) USER_NAME="$OPTARG" ;;
    d) DB_NAME="$OPTARG" ;;
    h) HOST="$OPTARG" ;;
    P) PORT="$OPTARG" ;;
    D) SQL_DIR="$OPTARG" ;;
    z) SQL_ZIP="$OPTARG" ;;
    c) MYSQL_BIN="$OPTARG" ;;
    l) LOG_DIR="$OPTARG" ;;
    y) PROMPT_PASS="no" ;;
    *) print_usage; exit 2 ;;
  esac
done

if [[ -z "${USER_NAME}" ]]; then
  echo "ERROR: -u USER is required" >&2
  print_usage; exit 2
fi

if [[ -z "${SQL_DIR}" && -z "${SQL_ZIP}" ]]; then
  echo "ERROR: Provide -D <dir> OR -z <zip>" >&2
  print_usage; exit 2
fi

# Check mysql client
if ! command -v "${MYSQL_BIN}" >/dev/null 2>&1; then
  echo "ERROR: mysql client not found ('${MYSQL_BIN}'). Add to PATH or pass -c." >&2
  exit 2
fi

# Prepare SQL directory (extract zip if needed)
TEMP_DIR=""
if [[ -n "${SQL_ZIP}" ]]; then
  if [[ ! -f "${SQL_ZIP}" ]]; then
    echo "ERROR: Zip not found: ${SQL_ZIP}" >&2
    exit 2
  fi
  if ! command -v unzip >/dev/null 2>&1; then
    echo "ERROR: 'unzip' is required to extract ${SQL_ZIP}" >&2
    exit 2
  fi
  TEMP_DIR="$(mktemp -d)"
  unzip -q "${SQL_ZIP}" -d "${TEMP_DIR}"
  SQL_DIR="${TEMP_DIR}"
fi

if [[ ! -d "${SQL_DIR}" ]]; then
  echo "ERROR: Directory not found: ${SQL_DIR}" >&2
  [[ -n "${TEMP_DIR}" ]] && rm -rf "${TEMP_DIR}"
  exit 2
fi

mkdir -p "${LOG_DIR}"

# Ask for password once, unless user opted out and provided MYSQL_PWD
if [[ "${PROMPT_PASS}" == "yes" && -z "${MYSQL_PWD:-}" ]]; then
  if [[ -t 0 ]]; then
    read -r -s -p "MySQL password for ${USER_NAME}@${HOST}:${PORT}: " MYSQL_PWD
    echo
    export MYSQL_PWD
  fi
fi

FAILS=0
RAN_FILES=()

run_file() {
  local file="$1"
  local use_db="$2"
  local base ; base="$(basename "$file")"
  local out="${LOG_DIR}/${base}.out.txt"
  local err="${LOG_DIR}/${base}.err.txt"

  echo ">> Running ${base} ..."

  if [[ "${use_db}" == "1" && -n "${DB_NAME}" ]]; then
    "${MYSQL_BIN}" -h "${HOST}" -P "${PORT}" -u "${USER_NAME}" -D "${DB_NAME}" --default-character-set=utf8mb4 < "${file}" >"${out}" 2>"${err}"
  else
    "${MYSQL_BIN}" -h "${HOST}" -P "${PORT}" -u "${USER_NAME}" --default-character-set=utf8mb4 < "${file}" >"${out}" 2>"${err}"
  fi
  local rc=$?
  if [[ ${rc} -eq 0 ]]; then
    echo "OK  ${base}"
  else
    echo "FAIL ${base} (rc=${rc}) — see ${err}"
    FAILS=$((FAILS+1))
  fi
  RAN_FILES+=("${file}")
}

# Helper to run a file if present
maybe_run() {
  local file="$1"
  if [[ -f "${file}" ]]; then
    run_file "${file}" "$2"
  fi
}

# ORDER:
maybe_run "${SQL_DIR}/00_database.sql" 0
maybe_run "${SQL_DIR}/01_prefix.sql"   1

# common numbered phases
for pat in "10_*.sql" "15_*.sql" "20_*.sql" "30_*.sql"; do
  shopt -s nullglob
  files=( "${SQL_DIR}"/${pat} )
  IFS=$'\n' files=( $(printf "%s\n" "${files[@]}" | sort) )
  for f in "${files[@]}"; do
    run_file "${f}" 1
  done
done

maybe_run "${SQL_DIR}/99_post.sql" 1

# any remaining .sql not yet run
declare -A seen
for f in "${RAN_FILES[@]}"; do seen["$f"]=1; done
shopt -s nullglob
others=( "${SQL_DIR}"/*.sql )
IFS=$'\n' others=( $(printf "%s\n" "${others[@]}" | sort) )
for f in "${others[@]}"; do
  if [[ -z "${seen[$f]:-}" ]]; then
    run_file "${f}" 1
  fi
done

if [[ ${FAILS} -gt 0 ]]; then
  echo
  echo "Completed with ${FAILS} error file(s). Check '${LOG_DIR}/*.err.txt'."
  [[ -n "${TEMP_DIR}" ]] && rm -rf "${TEMP_DIR}"
  exit 1
else
  echo
  echo "All SQL files ran successfully."
  [[ -n "${TEMP_DIR}" ]] && rm -rf "${TEMP_DIR}"
  exit 0
fi
