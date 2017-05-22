docker rm $(docker ps -a -q)

docker rmi $(docker images | grep "^<none>" | awk '{print $3}')

docker image rm docker_zookeeper
docker image rm docker_kafka1
docker image rm spring-boot/kafka 

