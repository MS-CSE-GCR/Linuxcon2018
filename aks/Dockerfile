FROM java:8

COPY target/aks-0.0.1-SNAPSHOT.jar /app/myapp.jar

EXPOSE 8080


WORKDIR /app
ENTRYPOINT ["java","-jar","/app/myapp.jar"]
