#!/usr/bin/env bash
set -euo pipefail

echo "Bootstrapping (warm compile)…"
# First compile (offline preferred, fallback online)
mvn -q -DskipTests -o compile || mvn -q -DskipTests compile

# Start Spring Boot (forked) so DevTools can restart the child process
echo "Starting Spring Boot (with DevTools) …"
mvn -q -DskipTests spring-boot:run \
  -Dspring-boot.run.fork=true \
  -Dspring-boot.run.addResources=true \
  -Dspring-boot.run.jvmArguments="\
-Dspring.devtools.restart.enabled=true \
-Dspring.devtools.restart.poll-interval=1s \
-Dspring.devtools.restart.quiet-period=300ms \
-Dspring.devtools.restart.trigger-file=.restart \
-Dspring.devtools.restart.additional-paths=/app/src,/app/src/main/resources \
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005" &
APP_PID=$!

# Graceful shutdown handler
cleanup() { kill -TERM "$APP_PID" 2>/dev/null || true; wait "$APP_PID" 2>/dev/null || true; }
trap cleanup INT TERM

echo "Watching src/ for changes (modify/create/delete)…"
# Watch for any change under src and recompile
while inotifywait -r -e modify,create,delete,move --format '%w%f' src; do
  echo "Change detected → recompiling…"
  # Try offline first for speed; fallback to online
  mvn -q -DskipTests -o compile || mvn -q -DskipTests compile

  # Touch .restart to guarantee DevTools sees a change even on pure resource edits
  touch .restart || true
done &

# Wait for Spring Boot
wait "$APP_PID"
