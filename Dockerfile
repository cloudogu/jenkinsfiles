FROM openjdk:8u151

COPY target/wildfly-kitchensink-swarm.jar /wildfly-kitchensink-swarm.jar

EXPOSE 8080

CMD ["sh", "-c", \
"java \
-jar \
wildfly-kitchensink-swarm.jar \
"]