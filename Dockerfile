FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY /target/resource-*.jar /app/ojt-minhyeok-resource.jar
COPY start.sh /app/start.sh
RUN chmod +x /app/start.sh

ENTRYPOINT ["/app/start.sh"]