FROM maven:3.9.14-eclipse-temurin-21
USER root
RUN mkdir -p /app
WORKDIR /app
COPY . /app
RUN chmod +x entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]