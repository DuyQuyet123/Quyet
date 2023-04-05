FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/ShopQA-1.0.0-RELEASE.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
ENV PROFILE="${PROFILE}" \
    DB_URL="" \
    DB_USER="" \
    DB_PASSWORD=""
CMD /deployments/run-java.sh --spring.profiles.active=${PROFILE} \
    --spring.application.name=${APPLICATION_NAME} \
    --spring.datasource.url=${DB_URL} \
    --spring.datasource.username=${DB_USER} \
    --spring.datasource.password=${DB_PASSWORD}