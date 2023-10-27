FROM adoptopenjdk/openjdk11
ARG port
ARG JAR_FILE_PATH=./bin/real-estate-admin-web.jar
COPY ${JAR_FILE_PATH} real-estate-admin-web.jar
EXPOSE ${port}
ENTRYPOINT ["java", "-jar", "real-estate-admin-web.jar"]
