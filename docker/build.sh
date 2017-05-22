rm *.jar
cp -rf  ../registry/target/registry-1.0-SNAPSHOT.jar registry.jar
cp -rf  ../config/target/config-1.0-SNAPSHOT.jar config.jar
cp -rf  ../dispatcher/target/dispatcher-1.0-SNAPSHOT.jar dispatcher.jar
cp -rf  ../groups/target/groups-1.0-SNAPSHOT.jar groups.jar
cp -rf  ../recipients/target/recipients-1.0-SNAPSHOT.jar recipients.jar

docker build -t spring-boot/kafka .
