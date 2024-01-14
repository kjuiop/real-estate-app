FROM adoptopenjdk/openjdk11
ARG port
ARG JAR_FILE_PATH=./bin/real-estate-admin-web.jar
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY ${JAR_FILE_PATH} real-estate-admin-web.jar
EXPOSE ${port}
ENTRYPOINT ["java", "-jar", "real-estate-admin-web.jar"]
