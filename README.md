Micro-services with Kafka. Fully asynchronous and not cohesive services.
------------------------------------------------------------------------


Project utilizes next technologies:
	- Micro-services: in our example two domain classes are used Recipient and Group.	  Each of them is located in separate project and managed (and deployed) 		  separately.
 	- Sprint Boot
        - Sprint Cloud Configuration
	- Sprint Eureka
	- Kafka/Zookeeper
	- Docker

------------------------------------------------------------------------

Enclosed maven modules:

1. common-beans : Common constants file,
2 simple domain object classes: Group & Recipient.
Domain objects relation: Recipients could be organized to groups and
Recipient class has group_id attribute.

2. config : Configuration Server based on Spring Cloud Config Server and Spring Cloud Eureka (spin off go the Netflix Eureka). Spring Cloud Config provides server and client-side support for externalized configuration in a distributed system. Spring Cloud Eureka is a service discovery service based on Netflix Eureka. 

In nutshell all services have an url to config service and get it's configuration parameters from the config service. Config service stores all the services configurations either as a set of files or in database, etc.	

3. dispatcher : main REST service, which has just two controllers for simplicity. It creates Groups and Recipients by putting objects to Kafka queue. Then correspondent service (either recipient or group) picks the messages up and creates actual database records.

4. docker (not a maven module) : creates a fully functional environment with all components (like kafka, zookeeper) configured and deployed

5. groups : A kafka consumer, which listens to the same topic as dispatcher populates once create group controller's endpoint is hit. Kafka message is consumed and database is populated. Also basic REST web services for groups provided: GET /groups/all, DELETE /groups/{id}, PUT /groups/{id}, GET /groups/{id}. Designed as a micro-service.

6. recipients : A kafka consumer, which listens to the same topic as dispatcher populates once create recipient controller's endpoint is hit. Kafka message is consumed and database is populated. GET /recipients/all, DELETE /recipients/{id}, PUT /recipients/{id}, GET /recipients/{id}. Designed as a micro-service.

7. registry :  an Eureka server. Eureka client registers the information about the running instance to the Eureka server. Registration happens on first heartbeat (after 30 seconds).



How to build and run:
------------------------------------------------------------------------
Prerequisites:
	- Apache Maven 3.2.5 or later
	- Docker 17 or later
	- Java 1.8
	- The code was tested on Mac and should ran likewise on Linux.


1. From the project's root run 'mvn clean install'. That should build all the components.
2. Build and run docker container:
	2.1 if you are not familiar with docker, you should probably read about it first. :)
	2.1 cd docker
	2.2 executing ./build.sh will build your docker container
	2.3 executing ./run.sh will start your docker container.

3. At this point two REST services http://localhost:8081/groups and http://localhost:8081/recipients should be active. You could use any http client (like 'curl') to make POST requests with JSON of the correspondent object (either Recipient or Group) as a body of the request. The JSON structures should match two classes: 
	      com.ponomaryov.groups.domain.Group
	      com.ponomaryov.recipients.domain.Recipient

Two JSON samples are provided below:
Group:

{"name":"GROUP100","description":"First recipients group"}

Recipient:
{"name":"RECIPIENT1","firstName":"John","lastName":"Lennon","email":"j.lennon@beatles.com", "dob":"1940-10-09T18:30:00.000Z", "groupId": "<id assigned at group creation>"}

4. How to test:
	Note, by default recipients REST web services run at http://localhost:8083/recipients and groups once run at http://localhost:8082/groups.
	After you successfully run http://localhost:8081/groups with group JSON above, you could run GET http://localhost:8082/groups/all to see if your group
	was added to the database. You could use group_id received in the response in Recipient JSON during POST http://localhost:8081/recipients.
	Like wise you could call GET http://localhost:8081/recipients/all to check if your recipient was created successfully.

5. TODO. Implement Security and Client Side Load Balancing (Ribbon - another Netflix product).
