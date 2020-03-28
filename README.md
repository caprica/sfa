# Some Fine Application

This very fine project is a fully working reference application focused around React on the front-end, Spring Boot in
the middle tier, and MongoDB for database persistence.

## Fine Features

The headline features successfully demonstrated by this project are:

 - Single Page Application for the front-end user interface, built using React (using `create-react-app`)
 - Spring Boot middle tier, providing web service end-points, services and data access repositories
 - MongoDB for the persistence tier
 - Spring Security providing authentication (user identity) services, and authorization (role/grant-based permissions)
 - Working JSON Web Token (JWT) security implementation, generation and validation of tokens, automatically adding a
   token (when available) to all client API requests
 - Fully working client-side routing in the front-end application
 - Fully working server-side routing, deep links via bookmarks or history will properly resolve to the front-end
   application for client-side routing
 - Web service documentation via Swagger, although if I'm honest I don't like the provided UI much
 - Unit tests for the front-end using `react-testing-library` and `Jest`
 - Unit tests for the middle-tier using modern JUnit
 - Integration tests for the application using Spring Boot testing
 - Java 11
 - Project Lombok, to reduce boilerplate - Lombok really is a very fine library
 - Simple mechanism to seed the database with sample data
 - Build/deploy with Docker and Docker Compose (example specification files provided in the project home directory)
 - Cypress integration testing
 
 This is an _example_ application, and as such there are *some* tests implemented but test coverage is considerably 
 short of 100%.

## Why?

I have a very strong bias to React and single page applications.

I like Spring Boot for the middle tier because Java really is very fine indeed, and Spring Boot makes things nice and
easy, especially with Lombok.

I did not find any single source of documentation or examples that had everything I wanted for my idea of the preferred
software architecture for my own projects, and those of my clients.

So this project is intended to satisfy all of these things.
 
## Very Fine Indeed
 
A major goal of this project is to demonstrate fully working routing of requests (both from the front-end user
interface and from API users) - to include server-side routing and client-side routing.
 
 - Proper routing of requests is the single most difficult and single most important problem to solve
 - To accomplish all of the desired routing use-cases requires very particular configuration
 - The Spring web configuration and security configuration for this is not straightforward or obvious, although once
   known and implemented the solution is not all that complicated

Routing broadly works as follows:
 
 - Any explicit paths (e.g. in controller request-mappings) always resolve to a server-side route
 - Any specific requests for static files, images etc, resolve correctly to be served from the embedded web server
   static directory
 - All unmatched requests get routed to the `index.html` page for the single page application, which will be loaded and
   then client-side routing takes over - links to client-side routes like "/home" will work, as will any deep link like
   "a/b/c/d/e"
 
## Not Quite So Fine

Some things are missing:
 
 - transactions, optimistic concurrency, versions in entities
 - on the client, interception of API responses on failed requests to automatically login and retry the request (e.g.
   on expiry of the initial authentication token)
 - building a Docker image is not (yet) demonstrated, but is now my preferred approach
 - requires MongoDB to be setup externally
 - no routes for "/error", you will see a Whitelabel standard error page - I would prefer to route this to an error
   page in the front-end application but I do not have this working yet (it seems kinda tricksy)
 - no linting, code coverage or any static analysis tools or reports integrated
 - some tests will fail unless you do a full build, including the front-end application, first (see below)

But the point of this project is not to demonstrate _everything_ since those things are demonstrated elsewhere.

## You May Not Agree How Fine This Is, But You Are Wrong

 - the front-end application is _not_ built by Maven (e.g. the `frontend` pluigin) - I do not like that plugin much,
   although it does work, I prefer now to build the front-end separately (usually as part of a build pipeline where the
   front-end is built directly with node and the resulting artifacts are copied to the appropriate place for the Maven
   build) and produce a Docker image
 - Jetty is preferred over Tomcat, because Jetty is very fine indeed and Tomcat isn't very fine at all.
 
## Things That Really Aren't Fine At all

 - warning messages from the JVM complaining that some Spring classes are using invalid accesses
 - warning message about MongoDB indexes and using a deprecated mechanism
 
## Credits

Over time I have used many similar approaches to get fully working client-side routing, server-side routing, *and*
Spring security integrated.

The cleanest/nicest approach for Spring Boot configuration was explained
[here](https://stackoverflow.com/a/50709789/2625478), so credit where it is due. 

## Building and Running

 1. Clone the repo.
 2. cd src/main/webapp
 3. npm start
 4. Run the SomeFineApplication class
 
The front-end application uses port :3030 as a proxy to the Spring Boot application running on port :8080.

To do a "proper" build with the front-end fully integrated, either integrate the Maven `frontend` plugin, or from the
src/main/webapp directory run:

 - npm run build && mkdir -p ../resources/static && cp -R build/* ../resources/static/

Then restart the Spring Boot application, and use :8080 rather than :3000 for the port.

Note that some things may not work as expected when using the proxy via :3000 - this should be obvious but can catch out
the unwary. Any server side API call that you try in from the browser address bar (e.g. a simple GET request, or the
Swagger documentation) will not work as expected. This is because the API does not exist on the proxy, the browser does
not know it should go to :8080 for your request. In such instances, using port :8080 will work instead.

e.g. http://localhost:8080/swagger-ui.html for the Swagger documentation.

## Other Fine Things
 
The Spring Boot development tools are enabled. In theory any time you make a change, it should restart the running
application with your changes. In practice, this doesn't always seem reliable - but, it *is* enabled.

Make sure to install a Lombok plugin for your IDE.

Make sure to run the `spring-boot:build-info` goal before your IDE build (this can be automated in IDE's like IntelliJ). 

In the `etc` directory is an `sfa.json` file, this can be exported into the Talend Restlet API tester Chrome extension
to help with API testing.

## Foot Note

This used to be in the `WebAppConfiguration` class, but seems to not be needed (keeping it here for reference):

```
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/**")
        .addResourceLocations("classpath:/static/");
}
```

## Terms of Use

It would be very fine of you that if you find this project useful you give some credit back to this project.

Apart from that, use this fully-working baseline project as a reference or a jump-start for your own project,  and if
you have any ideas please post issues or pull requests.
