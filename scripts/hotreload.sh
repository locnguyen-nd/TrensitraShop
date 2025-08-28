#!/bin/bash
# Advanced hot reload script with automatic compilation
echo "🚀 Starting Spring Boot with Auto-Compile Hot Reload..."
echo "📁 Monitoring: src/main/java and src/main/resources"
echo "🔄 Auto-compile: Enabled"
echo "🌐 LiveReload: http://localhost:35729"

# Function to compile and restart
compile_and_trigger() {
    echo "⚡ Compiling changes..."
    ./mvnw compile -q
    if [ $? -eq 0 ]; then
        echo "✅ Compilation successful - Spring DevTools will restart"
        # Touch a file in the classpath to trigger Spring DevTools restart
        touch target/classes/.restart-trigger
    else
        echo "❌ Compilation failed"
    fi
}

# Start Spring Boot in background
./mvnw spring-boot:run &
SPRING_PID=$!

# Monitor for file changes
while inotifywait -r -e modify,create,delete src/main/ 2>/dev/null; do
    sleep 1  # Debounce multiple file changes
    compile_and_trigger
done

# Clean up
kill $SPRING_PID 2>/dev/null