# Dockerfile for React application
FROM node:18 as build

# 작업 디렉토리 설정
WORKDIR /app

# Node.js 메모리 제한 확장
ENV NODE_OPTIONS="--max-old-space-size=4096"

# 의존성 설치
COPY package.json package-lock.json ./
RUN npm install

# 코드 복사 및 빌드
COPY ./ .
RUN npm run build

# Nginx를 사용하여 React 애플리케이션 서빙
FROM kiweeteam/nginx-brotli:latest

# 빌드된 파일을 Nginx 컨테이너에 복사
COPY --from=build /app/build /usr/share/nginx/html

