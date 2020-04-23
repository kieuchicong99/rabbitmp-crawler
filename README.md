# RUN RABBITMQ container
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

# INSTALL packgage 
mvn install

# BUILD APP

mvn compile

# RUN APP
mvn exec:java -Dexec.mainClass="mom.crawler.App"

