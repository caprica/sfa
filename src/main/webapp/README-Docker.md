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

## Starting a MongoDB container

Tweak options to suit, here the options include "-it --rm" which gives me an interactive session and removes the
container on exit.

```
docker run -it --rm --name=sfa-db -p27017-27019:27017-27019 mongo:4.2.3
```

You may like to run in detached mode with "-d".

You should now be able to access this MongoDB database within the container using regular tools.

### Why three port numbers?
27017 is the default port for the database itself.

27018 is the default port when using `shardsvr`.

27019 is the default port when using `configsvr`.

It likely is not necessary to map all of those ports.

## Starting the application container

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
