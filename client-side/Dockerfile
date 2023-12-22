FROM nginx:1.21.5-alpine
RUN rm /etc/nginx/conf.d/default.conf
COPY dist/client-side /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
