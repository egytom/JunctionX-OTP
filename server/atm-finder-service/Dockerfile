FROM openjdk:8-jre-alpine

ENV APP_FILE atm-finder.jar
ENV APP_HOME /usr/apps

EXPOSE 8080
COPY target/$APP_FILE $APP_HOME/
WORKDIR $APP_HOME
COPY files/data.json files/

ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE"]
