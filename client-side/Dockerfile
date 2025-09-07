# ---------------------------
# Stage 1: Build Angular app
# ---------------------------
FROM node:18.20.4-alpine3.20 AS build
WORKDIR /app

# Install dependencies
COPY package*.json ./
RUN npm ci --legacy-peer-deps

# Copy source code and build
COPY . .
RUN npm run build --prod

# ---------------------------
# Stage 2: Serve with Nginx
# ---------------------------
FROM nginx:1.27-alpine

# Remove default Nginx page
RUN rm -rf /usr/share/nginx/html/*

# Copy built Angular app from build stage
COPY --from=build /app/dist /usr/share/nginx/html

# Copy custom nginx config (optional, e.g., for Angular routing)
# Make sure you have nginx.conf in your project root
# COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port
EXPOSE 80

# Run nginx
CMD ["nginx", "-g", "daemon off;"]

