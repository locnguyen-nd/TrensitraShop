# 🐳 Docker Setup for Trendista Backend

## 📋 System Requirements

- Docker Desktop running
- Docker Compose
- Ports 8080 and 3308 must be available

## 🚀 How to use

### 1. Production Mode

```bash
# Run the script automatically
./scripts/prod.sh

# Or run manually
docker-compose up --build -d
```

### 2. Development Mode (with Hot Reload - Ultra-Optimized)

```bash
# Run the script automatically with ultra-fast hot reload (Recommended)
./scripts/dev-fast.sh

# Or run manually
docker-compose -f docker-compose.dev.yml up --build -d
```

**⚡ Hot Reload ULTRA-optimized:**
- **Reload time**: 3-8 seconds (was 40 seconds!)
- **Polling interval**: 200ms (was 500ms)
- **Quiet period**: 100ms (was 200ms)
- **Compile loop**: 200ms (was 500ms)
- **Monitor only Java files** to avoid unnecessary reloads
- **Exclude static files** from triggering reloads
- **Disabled condition evaluation logging** for faster startup

## 🔧 Useful commands

```bash
# View logs
docker-compose logs -f # Production
docker-compose -f docker-compose.dev.yml logs -f # Development

# Stop services
docker-compose down # Production
docker-compose -f docker-compose.dev.yml down # Development

# View status
docker-compose ps # Production
docker-compose -f docker-compose.dev.yml ps # Development

# Restart services
docker-compose restart # Production
docker-compose -f docker-compose.dev.yml restart # Development

# Clear cache
docker kill $(docker ps -q)
docker rmi $(docker images -a -q)
```

## 🌐 Access the application

- **Backend API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/actuator/health
- **MySQL Database**: localhost:3308

## 📊 Database Information

- **Host**: mysql (in Docker network) or localhost:3308
- **Database**: trendista
- **Username**: root
- **Password**: root

## 🔍 Troubleshooting

### If you get an error that the port is already in use:

```bash
# Check which port is in use
lsof -i :8080
lsof -i :3308

# Kill the process using the port
kill -9 <PID>
```

### If you get an error that the port is already in use:

```bash
# Check network list
docker network ls

# remove network
docker network rm <network_name_or_id>
# remove multiple networks
docker network rm <network1_name_or_id> <network2_name_or_id> ...

# remove all networks
docker network prune
```

### If you get a Docker error:

```bash
# Clean up completely
docker-compose down -v --rmi all
docker system prune -a

# Restart
./scripts/docker-run.sh

```

### If MySQL fails to connect:

```bash
# Check MySQL container
docker-compose logs mysql

# Restart MySQL
docker-compose restart mysql
```

## 📝 Notes

- **Production Mode**: Uses built JAR file, no hot reload
- **Development Mode**: Has optimized hot reload, source code is mounted into container
- Database password in Docker is set to `root` (matches `application.properties`)
- Health checks are enabled to ensure services start in the correct order

## 🚀 Tips for optimizing Development

### Fastest Hot Reload:
- **Edit Java files** only in `src/main/java` - reload in 3-8 seconds
- **Avoid editing `pom.xml`** - needs to restart container
- **Avoid editing static files** - not needed for backend development

### Monitoring:
```bash
# View real-time logs
docker-compose -f docker-compose.dev.yml logs -f backend-dev

# Check status
docker-compose -f docker-compose.dev.yml ps

# Quick restart if needed
docker-compose -f docker-compose.dev.yml restart backend-dev
```

## 🆘 Support

If you have problems, check:
1. Is Docker Desktop running
2. Are there any conflicting ports
3. Logs of containers
4. Are networks and volumes created correctly