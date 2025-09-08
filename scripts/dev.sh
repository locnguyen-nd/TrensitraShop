#!/bin/bash
# Script to run Docker Development Mode for Trendista Backend with Hot Reload
echo "Starting Trendista Backend with Docker Development Mode..."

# Get the directory where this script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# Get the project root directory (parent of scripts directory)
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"

# Change to project root directory
cd "$PROJECT_ROOT"

echo "Working directory: $(pwd)"
echo "Looking for docker-compose.dev.yml..."

# Check if docker-compose.dev.yml exists
if [ ! -f "docker-compose.dev.yml" ]; then
    echo "Error: docker-compose.dev.yml not found in current directory"
    echo "Current directory: $(pwd)"
    echo "Files in directory:"
    ls -la
    exit 1
fi

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "Docker is not running. Please start Docker first."
    exit 1
fi

# Stop and delete existing containers if any
echo "Cleaning up old containers..."
docker-compose -f docker-compose.dev.yml down -v

# Delete old images if any
echo "Cleaning old images..."
docker-compose -f docker-compose.dev.yml down --rmi all

# Build and run with optimized settings
echo "Building and starting services (Development Mode with Hot Reload)..."
if ! docker-compose -p trendista-dev -f docker-compose.dev.yml up --build -d; then
    echo "Failed to start services. Checking logs..."
    docker-compose -f docker-compose.dev.yml logs backend-dev
    exit 1
fi

# Wait for services to start
echo "Waiting for services to start..."
sleep 20

# Check status
echo "Services status:"
docker-compose -f docker-compose.dev.yml ps

# Check backend logs
echo "Backend logs:"
docker-compose -f docker-compose.dev.yml logs --tail=50 backend-dev

echo ""
echo "Done! Backend Development Mode is running!"
echo "Backend: http://localhost:8080"
echo "Swagger UI: http://localhost:8080/swagger-ui.html"
echo "Health check: http://localhost:8080/actuator/health"
echo "MySQL: localhost:3308"
echo "LiveReload: http://localhost:35729"
echo ""
echo "Useful commands:"
echo "   - View logs: docker-compose -f docker-compose.dev.yml logs -f backend-dev"
echo "   - View all logs: docker-compose -f docker-compose.dev.yml logs -f"
echo "   - Stop services: docker-compose -f docker-compose.dev.yml down"
echo "   - Restart backend only: docker-compose -f docker-compose.dev.yml restart backend-dev"