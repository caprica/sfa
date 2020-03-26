# Docker Notes

## Building the application image

There is a `Dockerfile` in the project directory.

Make sure to build the front-end first (I do not integrate this with Maven because reasons explained elsewhere).

```
cd src/main/webapp
npm run build && mkdir -p ../resources/static && cp -R build/* ../resources/static/
```

Back in the project directory:

```
docker build . -t caprica/sfa:latest
```

## The Hard Way

See later for an easier way, but for the record first...

### Starting a MongoDB container

Tweak options to suit, here the options include "-it --rm" which gives me an interactive session and removes the
container on exit.

```
docker run -it --rm --name=sfa-db -p27017-27019:27017-27019 mongo:4.2.3
```

You may like to run in detached mode with "-d".

You should now be able to access this MongoDB database within the container using regular tools.

#### Why three port numbers?
27017 is the default port for the database itself.

27018 is the default port when using `shardsvr`.

27019 is the default port when using `configsvr`.

It likely is not necessary to map all of those ports.

### Starting the application container

Again, "-it --rm" are only used in this example, they would not typically be used for ordinary running.

```
docker run -it --rm -p8080:8080 --name sfa caprica/sfa:latest
```

Not fully understanding the intricacies of container routing, instead of the above command this was required so the
application container could "see" the database container:

```
docker run -it --rm --network="host" --name sfa caprica/sfa:latest
```

Instead of mapping the ports, the "host" networking is used.

This is not optimal, but it is enough to get everything up and running on localhost.

### Port mapping

In this example:

```
docker run -p80:8080 --name sfa caprica/sfa:latest
```

8080 is the port exposed by the application and 80 is the port mapping (that you would use from e.g. a web browser).

Using the same value for both is perfectly fine.

## The Easy Way

Use `docker-compose`.

### Deploying the whole thing

Describe the entire application (the web-service and the database) in a `docker-compose.yml` file and have
`docker-compose` start everything and stitch everything together - importantly here a common network will by default be
created and that network will allow all the deployed services to "see" each other.

See the example file in the project root directory, and then:

```
docker-compose up
```

Use "-d" to run containers in detached mode.

### Stopping containers

```
docker-compose stop
```

### Tearing it all down again

You do _not_ need to use `stop` first:

```
docker-compose down
```

### Notes on docker-compose

The provided specification files are just examples, a lot more can be done with them (such as declaring persistent
storage volumes).

## Using the application

In a browser, go to http://localhost:8080 and everything should work.

## Other useful commands

Removing unused/dangling containers:

```
docker images prune
docker images prune -a
```

Listing containers:

```
docker container ls
docker container ls -a
```

Listing current processes:

```
docker ps
docker ps -a
```

Removing containers, where in this example `sda-db` is the container name:

```
docker container rm sfa-db
```

If you happen to be running MongoDB locally (most likely on the same port as the container version), you can stop the
local `mongod` with:

```
sudo service mongodb stop

```

## Other notes

By default, Docker will allow outgoing requests on _all_ ports, they do not need to be mapped.


# Docker Compose

## Installation

```
sudo curl -L "https://github.com/docker/compose/releases/download/1.25.4/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
```

### Post-installation:

```
cd /usr/local/bin
sudo chmod u+x docker-compose
```
