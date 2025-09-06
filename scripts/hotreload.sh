#!/bin/bash
# Hot reload script with file monitoring and auto-compilation
echo "🚀 Starting Spring Boot with File Monitoring Hot Reload..."
echo "📁 Monitoring: src/main/java and src/main/resources"
echo "🔄 Auto-compile: Enabled"
echo "🌐 LiveReload: http://localhost:35729"

# Cleanup function
cleanup() {
    echo "🛑 Stopping Spring Boot..."
    kill $SPRING_PID 2>/dev/null
    kill $WATCH_PID 2>/dev/null
    exit 0
}

# Set up signal handlers
trap cleanup SIGTERM SIGINT

# Function to compile changes
compile_changes() {
    echo "⚡ Compiling changes..."
    ./mvnw compile -q
    if [ $? -eq 0 ]; then
        echo "✅ Compilation successful - Spring DevTools will restart"
    else
        echo "❌ Compilation failed"
    fi
}

# Start Spring Boot in background
./mvnw spring-boot:run &
SPRING_PID=$!

# Wait for initial startup
sleep 5

# Monitor file changes and trigger compilation
while true; do
    if inotifywait -r -e modify,create,delete src/main/ 2>/dev/null; then
        # Debounce multiple rapid changes
        sleep 1
        
        # Check if more changes are coming
        if ! timeout 1 inotifywait -r -e modify,create,delete src/main/ 2>/dev/null; then
            compile_changes
        fi
    fi
done &
WATCH_PID=$!

# Wait for Spring Boot process
wait $SPRING_PID