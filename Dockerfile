FROM maven:latest
RUN mkdir /shopBee
WORKDIR /shopBee
COPY . .
EXPOSE 8080
CMD ["mvn", "spring-boot:run"]
