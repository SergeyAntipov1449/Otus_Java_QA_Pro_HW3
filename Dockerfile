FROM maven:3.9.14-eclipse-temurin-25 AS build
USER root
RUN mkdir -p /root/api_test
WORKDIR /root/api_test
COPY . /root/api_test
ENTRYPOINT ["entrypoint.sh"]