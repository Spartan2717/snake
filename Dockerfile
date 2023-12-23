# Usa un'immagine Maven per la build
FROM maven:3.8.4-openjdk-11 AS build

# Imposta la directory di lavoro nell'ambiente di build
WORKDIR /app

# Copia il file POM e scarica le dipendenze
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia il resto del codice dell'applicazione
COPY src ./src

# Compila l'applicazione e crea il file JAR
RUN mvn clean package

# Usa un'immagine OpenJDK più leggera come base per l'immagine finale
FROM adoptopenjdk:11-jre-hotspot

# Installa dipendenze per l'interfaccia grafica
RUN apt-get update && \
    apt-get install -y \
    xvfb \
    xauth \
    libxrender1 \
    libxtst6 \
    libxi6

# Imposta la variabile di ambiente per Xvfb
ENV DISPLAY=:99

# Imposta la directory di lavoro nel container
WORKDIR /app

# Copia il file JAR dalla fase di build al container
COPY --from=build /app/target/snake-1.0.jar /app/snake.jar

# Comando per eseguire l'applicazione
CMD ["xvfb-run", "java", "-jar", "snake.jar"]
