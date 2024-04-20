FROM adoptopenjdk/openjdk11 AS build
ARG JAR_FILE_PATH=./bin/real-estate-admin-web.jar
COPY ${JAR_FILE_PATH} real-estate-admin-web.jar

FROM alpine:latest
ARG port
ENV TZ=Asia/Seoul
RUN apk add --no-cache openjdk11-jre-headless tzdata && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone
COPY --from=build /real-estate-admin-web.jar /real-estate-admin-web.jar
EXPOSE ${port}
ENTRYPOINT ["java", "-jar", "real-estate-admin-web.jar"]

#ENTRYPOINT ["tail", "-f", "/dev/null"]