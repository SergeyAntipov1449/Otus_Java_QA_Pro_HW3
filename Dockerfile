FROM maven:3.9.14-eclipse-temurin-21
USER root
RUN mkdir -p /root/api_test
WORKDIR /root/api_test
COPY . /root/api_test
RUN chmod +x entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]