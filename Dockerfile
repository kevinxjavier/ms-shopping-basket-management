FROM openjdk:18
LABEL Descripcion="CSV items product calculate IVA and total" Autor="Kevin piña" Version="0.0.1"
MAINTAINER kevin@kevinpina.com
WORKDIR microservice
ADD ms-shopping-basket-management-boot/target/shopping-basket-management-boot-0.0.1-SNAPSHOT.jar shopping-basket-management.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/microservice/shopping-basket-management.jar"]
