FROM maven:3.8.4-openjdk-11
WORKDIR /app
COPY . /app
CMD ["mvn", "clean", "install","exec:java"]