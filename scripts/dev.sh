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
$DC -f docker-compose.dev.yml down -v

echo "▶ Building and starting DEV stack…"
$DC -f docker-compose.dev.yml up --build -d

echo "⏳ Waiting for services to be healthy…"
$DC -f docker-compose.dev.yml ps

echo
echo "DEV is up! Endpoints:"
echo "  • Backend       : http://localhost:8080 (profile=dev)"
echo "  • MySQL         : localhost:3309"
echo "  • LiveReload    : ws://localhost:35729"
