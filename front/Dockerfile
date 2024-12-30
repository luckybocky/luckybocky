# Dockerfile for React application
FROM node:18 as build

# 작업 디렉토리 설정
WORKDIR /app

# 의존성 설치
COPY package.json package-lock.json ./
RUN npm install

# 코드 복사 및 빌드
COPY ./ .
RUN npm run build

# Nginx를 사용하여 React 애플리케이션 서빙
FROM nginx:alpine

COPY default.conf /etc/nginx/conf.d/default.conf

# 빌드된 파일을 Nginx 컨테이너에 복사
COPY --from=build /app/build /usr/share/nginx/html

# Nginx 포트 열기
EXPOSE 80

# Nginx 실행
CMD ["nginx", "-g", "daemon off;"]

