# Étape 1: Utiliser une image Maven avec OpenJDK 21 pour compiler le projet
FROM maven:3-amazoncorretto-21 AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier POM et le répertoire de code source
COPY pom.xml /app
COPY src /app/src

# Compiler le projet Maven
RUN mvn clean install -DskipTests

# Étape 2: Utiliser une image JDK 21 pour exécuter l'application
FROM openjdk:21-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le JAR compilé de l'étape de build
COPY --from=build /app/target/myminibank-0.0.1-SNAPSHOT.jar /app/myminibank.jar

# Exposer le port sur lequel l'application écoutera
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "/app/myminibank.jar"]