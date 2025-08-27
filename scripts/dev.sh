#!/bin/bash
# Script to run Docker Development Mode for Trendista Backend
echo "Start Trendista Backend with Docker Development Mode..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "Docker is not running. Please start Docker first."
    exit 1
fi

# Stop and delete old containers if any
echo "Cleaning up old containers..."
docker-compose -f docker-compose.dev.yml down -v

# Delete old images if any
echo "Deleting old images..."
docker-compose -f docker-compose.dev.yml down --rmi all

# Build and run
echo "Building and starting services (Development Mode)..."
docker-compose -p trendista-dev -f docker-compose.dev.yml up --build -d

# Wait for services to start
echo "Wait for services to start..."
sleep 15

# Check status
echo "Services status:"
docker-compose -f docker-compose.dev.yml ps

echo "✅ Done! Backend Development Mode is running at http://localhost:8080"
echo "📚 Swagger UI: http://localhost:8080/swagger-ui.html"
echo "🔍 Health check: http://localhost:8080/actuator/health"
echo "🗄️ MySQL: localhost:3308"
echo ""
echo "💡 Hot reload is enabled - code changes will automatically restart"
echo "💡 To view logs: docker-compose -f docker-compose.dev.yml logs -f"
echo "💡 To stop: docker-compose -f docker-compose.dev.yml down"