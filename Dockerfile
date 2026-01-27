# 1. 베이스 이미지 설정 (가벼운 Alpine Linux와 Java 17 조합)
FROM eclipse-temurin:17-jdk-alpine

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 빌드된 JAR 파일을 컨테이너 내부로 복사
# build/libs/*.jar 로 지정하면 버전 숫자가 바뀌어도 대응 가능합니다.
COPY build/libs/*.jar app.jar

# 4. 애플리케이션 실행 (8080 포트 사용)
EXPOSE 8080

# 5. 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]