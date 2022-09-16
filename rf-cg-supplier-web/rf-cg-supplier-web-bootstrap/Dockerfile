FROM java:11
VOLUME /tmp
ADD rf-cg-supplier-web-bootstrap-1.0-SNAPSHOT.jar supweb.jar
RUN sh -c 'touch /supweb.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /supweb.jar" ]