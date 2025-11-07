# Etapa 1: Build da aplicação com Maven
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o pom.xml e baixa dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte e faz o build
COPY ProfitFlow/src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final (somente o JAR)
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copia o JAR gerado (usa wildcard para qualquer nome)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
