@echo off
set BUILD_ENV_CLIENT=docker
set DOCKER_COMPOSE_FILE=docker-compose.yaml
set SELENIUM_GRID_DOCKER_COMPOSE_FILE=selenium-compose.yaml

cd /d %~dp0..\crypto-portfolio-microservices\crypto-price-service\
call mvn clean package -DskipTests

cd /d %~dp0..\crypto-portfolio-microservices\gateway-service\
call mvn clean package -DskipTests

cd /d %~dp0..\crypto-portfolio-microservices\discovery-server\
call mvn clean package -DskipTests

cd /d %~dp0..\crypto-portfolio-microservices\wallet-service\
call mvn clean package -DskipTests

cd /d %~dp0..\crypto-portfolio-microservices\client-side\
call npm install --legacy-peer-deps @angular-devkit/build-angular

cd /d %~dp0..\crypto-portfolio-microservices\client-side\
call npm install -g @angular/cli

cd /d %~dp0..\crypto-portfolio-microservices\client-side\
call ng build --configuration=%BUILD_ENV_CLIENT%

cd /d %~dp0..\crypto-portfolio-microservices\quality-assurance\qa-ui\
call docker-compose -f %SELENIUM_GRID_DOCKER_COMPOSE_FILE% down

cd /d %~dp0..\crypto-portfolio-microservices\
call docker-compose -f %DOCKER_COMPOSE_FILE% down

cd /d %~dp0..\crypto-portfolio-microservices\
call docker-compose -f %DOCKER_COMPOSE_FILE% up -d --build

cd /d %~dp0..\crypto-portfolio-microservices\quality-assurance\qa-ui\
call docker-compose -f %SELENIUM_GRID_DOCKER_COMPOSE_FILE% up -d --build