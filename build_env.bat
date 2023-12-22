@echo off
set BUILD_ENV_CLIENT=local
set DOCKER_COMPOSE_FILE=docker-compose-local.yaml

cd /d %~dp0..\crypto-portfolio-microservices\crypto-price-service\
call mvn clean package -DskipTests

cd /d %~dp0..\crypto-portfolio-microservices\gateway-service\
call mvn clean package -DskipTests

cd /d %~dp0..\crypto-portfolio-microservices\discovery-server\
call mvn clean package -DskipTests

cd /d %~dp0..\crypto-portfolio-microservices\wallet-service\
call mvn clean package -DskipTests

cd /d %~dp0..\crypto-portfolio-microservices\client-side\
call ng build --configuration=%BUILD_ENV_CLIENT%

cd /d %~dp0..\crypto-portfolio-microservices\
call docker-compose -f %DOCKER_COMPOSE_FILE% down

cd /d %~dp0..\crypto-portfolio-microservices\
call docker-compose -f %DOCKER_COMPOSE_FILE% up -d --build