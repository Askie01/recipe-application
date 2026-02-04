FROM eclipse-temurin:21-jre-jammy

RUN apt-get update \
 && apt-get install -y --no-install-recommends curl \
 && rm -rf /var/lib/apt/lists/* \
 && useradd -ms /bin/bash appuser

USER appuser
WORKDIR /app

COPY --chown=appuser:appuser target/recipe-application*.jar /app/app.jar

ENTRYPOINT ["java","-jar","/app/app.jar", "--spring.profiles.active=mysql"]