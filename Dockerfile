#Verwenden Sie die Leichtgewicht Laufzeitumgebung, die auf java 25 ausgerichtet ist
FROM eclipse-temurin:25-jre-alpine
#Das Arbeitsverzeichis innerhalb des Containers festlegen
WORKDIR /app

# Kopiere das erstellte Fat-Jar aus dem target Ordner
COPY target/trading-orders-service-0.0.1-SNAPSHOT.jar /app/trading-orders-service-0.0.1-SNAPSHOT.jar

EXPOSE 7080

CMD ["java", "-jar", "trading-orders-service-0.0.1-SNAPSHOT.jar"]


