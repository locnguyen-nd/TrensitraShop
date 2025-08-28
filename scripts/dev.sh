#!/bin/bash
# Script to run Docker Development Mode for Trendista Backend with Hot Reload
echo "🚀 Starting Trendista Backend with Docker Development Mode..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker first."
    exit 1
fi

# Stop and delete existing containers if any
echo "🛑 Cleaning up old containers..."
docker-compose -p trendista-dev -f docker-compose.dev.yml down -v

# Delete old images if any
echo "🧹 Cleaning old images..."
docker-compose -p trendista-dev -f docker-compose.dev.yml down --rmi all

# Build and run with optimized settings
echo "🔨 Building and starting services (Development Mode with Hot Reload)..."
docker-compose -p trendista-dev -f docker-compose.dev.yml up --build -d

# Wait for services to start
echo "⏳ Waiting for services to start..."
sleep 20

# Check status
echo "Services status:"
docker-compose -f docker-compose.dev.yml ps

echo ""
echo "✅ Done! Backend Development Mode is running!"
echo "🌐 Backend: http://localhost:8080"
echo "📚 Swagger UI: http://localhost:8080/swagger-ui.html"
echo "🔍 Health check: http://localhost:8080/actuator/health"
echo "🗄️ MySQL: localhost:3308"
echo "🔄 LiveReload: http://localhost:35729"
echo ""
echo "🔥 Simplified Hot Reload Features:"
echo "   - ✨ Spring DevTools handles automatic restart (3-5 seconds)"
echo "   - 📁 Watches .java, .properties, .yml files automatically"
echo "   - 🚀 Simple and reliable - just like local development"
echo "   - 📝 No complex file watchers or manual compilation"
echo ""
echo "💡 Useful commands:"
echo "   - View logs: docker-compose -f docker-compose.dev.yml logs -f backend-dev"
echo "   - View all logs: docker-compose -f docker-compose.dev.yml logs -f"
echo "   - Stop services: docker-compose -f docker-compose.dev.yml down"
echo "   - Restart backend only: docker-compose -f docker-compose.dev.yml restart backend-dev"
echo ""
echo "🎯 To test hot reload:"
echo "   1. Edit any Java file in src/main/java"
echo "   2. Save the file"
echo "   3. Watch logs: docker logs trendista-backend-dev -f"
echo "   4. Spring DevTools will automatically restart in 3-5 seconds"
echo "   5. Verify changes at http://localhost:8080"