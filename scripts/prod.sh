#!/bin/bash
# Script to run Docker for Trendista Backend
echo "Start Trendista Backend with Docker..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "Docker is not running. Please start Docker first."
    exit 1
fi

# Stop and delete old containers if any
echo "Cleaning up old containers..."
docker-compose down -v

# Delete old images if any
echo "Deleting old images..."
docker-compose down --rmi all

# Build and run
echo "Building and starting services..."
docker-compose -p trendista up --build -d

# Wait for services to start
echo "Waiting for services to start..."
sleep 20

# Check status
echo "Services status:"
docker-compose ps

echo "✅ Done! Backend is running at http://localhost:8080"
echo "📚 Swagger UI: http://localhost:8080/swagger-ui.html"
echo "🔍 Health check: http://localhost:8080/actuator/health"
echo "🗄️ MySQL: localhost:3308"
echo ""
echo "💡 To view logs: docker-compose logs -f"
echo "💡 To stop: docker-compose down"