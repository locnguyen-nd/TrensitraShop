#!/usr/bin/env bash
set -Eeuo pipefail

# Detect docker compose command
if docker compose version >/dev/null 2>&1; then
  DC="docker compose"
elif docker-compose version >/dev/null 2>&1; then
  DC="docker-compose"
else
  echo "docker compose not found. Please install Docker Desktop / Compose v2."
  exit 1
fi

echo "▶ Stopping and removing old DEV stack…"
$DC down -v

echo "▶ Building and starting PROD stack…"
$DC up --build -d

echo "⏳ Waiting for services to be healthy…"
$DC ps

echo
echo "PROD is up! Endpoints:"
echo "  • Backend       : http://localhost:8080 (profile=production)"
echo "  • MySQL         : localhost:3309"
echo "  • Actuator      : http://localhost:8080/actuator/health"
