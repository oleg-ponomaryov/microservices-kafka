FROM openjdk
ENV CONFIG_SERVICE_PASSWORD SecretConfigPassword
ARG brokerId=1
RUN cd /
RUN wget http://www-us.apache.org/dist/kafka/0.10.1.0/kafka_2.11-0.10.1.0.tgz
RUN tar xvzf kafka_2.11-0.10.1.0.tgz

RUN mv /kafka_2.11-0.10.1.0 /kafka && \
sed -i s/localhost:2181/zookeeper:2181/g /kafka/config/server.properties && \
sed -i s/broker.id=0/broker.id=${brokerId}/g /kafka/config/server.properties
RUN sed -i s/127.0.0.1:2181/zookeeper:2181/g /kafka/config/consumer.properties

WORKDIR /apps
COPY config.jar config/
COPY dispatcher.jar dispatcher/
COPY recipients.jar recipients/
COPY groups.jar groups/
COPY registry.jar registry/
EXPOSE 2181 9092
