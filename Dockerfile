# Use a JRE11 image as the base.
#
# e.g.
#  adoptopenjdk:11-jre-hotspot
#  adoptopenjdk/openjdk11:x86_64-ubuntu-jre-11.0.4_11
#  adoptopenjdk/openjdk11:x86_64-alpine-jre-11.0.6_10
#
# Note that using a different base operating system may require different commands (e.g. groupadd vs addgroup)

#FROM adoptopenjdk:11-jre-hotspot
FROM adoptopenjdk/openjdk11:x86_64-alpine-jre-11.0.6_10

LABEL maintainer="mark.lee@capricasoftware.co.uk"

# Define a profile and group that will be used to run the application.
ARG GROUP=sfa
ARG USER=sfa

# Define a directory to contain the application artifacts.
ARG APP_DIR=app

# Create a new profile and group.
#RUN groupadd ${GROUP} && useradd -g ${GROUP} -s /bin/sh ${USER}
RUN addgroup ${GROUP} && adduser -G ${GROUP} -s /bin/sh -D ${USER}

# Create and prepare the application directory.
RUN mkdir -p ${APP_DIR}
RUN mkdir -p ${APP_DIR}/logs
RUN chown -R ${USER}:${GROUP} app

# Switch profile and working directory.
USER ${USER}:${GROUP}
WORKDIR ${APP_DIR}

# Copy the application artifact to the application directory.
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} sfa.jar

# Start the application.
ENTRYPOINT ["java", "-jar", "sfa.jar"]

# Instead, to enable remote debugging
#ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "sfa.jar"]
