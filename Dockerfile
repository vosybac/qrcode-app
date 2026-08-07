FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn -q -B clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/qrcode-app-1.0.0.jar app.jar
EXPOSE 8080
CMD java -Xmx256m -jar app.jar --server.port=$PORT
