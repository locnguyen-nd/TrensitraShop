#!/bin/bash
echo "Starting TrendistaShop Backend Local Development"
echo "================================================="

# Check if .env.local exists
if [ ! -f .env.local ]; then
    echo "File .env.local not found!"
    echo "Please create .env.local with required environment variables."
    exit 1
fi

echo "Loading environment variables from .env.local..."

# Export all variables from .env.local
export $(grep -v '^#' .env.local | xargs)

echo "Environment variables loaded:"
echo "   - Database: ${DB_HOST}:${DB_PORT}/${DB_NAME}"
echo "   - User: ${DB_USERNAME}"
echo "   - Profile: dev"

echo ""
echo "Starting Spring Boot application..."
echo "   - URL: http://localhost:8080"
echo "   - Swagger: http://localhost:8080/swagger-ui.html"
echo "   - Health: http://localhost:8080/actuator/health"

./mvnw spring-boot:run -Dspring-boot.run.profiles=dev