FROM node:latest AS builder

WORKDIR /opt/web
COPY package.json package-lock.json ./
RUN npm install

ENV PATH="./node_modules/.bin:$PATH"

COPY . ./
RUN ng build

FROM nginx:latest
COPY nginx.config /etc/nginx/conf.d/default.conf
COPY --from=builder /opt/web/dist/main-frontend /usr/share/nginx/html