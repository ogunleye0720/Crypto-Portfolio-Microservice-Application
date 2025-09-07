# Stage 1: Build Angular app
FROM node:18-alpine as build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build --prod

# Stage 2: Serve with NGINX
FROM nginx:1.21.5-alpine
RUN rm /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/client-side /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
