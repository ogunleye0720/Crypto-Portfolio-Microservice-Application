# Stage 1: Build Angular app
FROM node:18-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build --prod

# Stage 2: Final Nginx image
FROM nginx:1.27-alpine3.21 AS final

# Upgrade Alpine packages to latest security patches (fixes libxml2 CVEs)
RUN apk --no-cache upgrade

# Clean default config and copy our own
RUN rm /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
