# Cypress Integration Testing

[Cypress](https://www.cypress.io) is just amazing.

## Installing Cypress

Essentially:

```
npm install cypress --save-dev
```

## Configuration

Look for `cypress.json`.

Configure the `baseUrl` property appropriately:

```
{
    "baseUrl": "http://localhost:3000/"
}
```

You can use port 3000 for the React development proxy, or 8080 for the fully built application (remember to build the
React application and copy it to the web application if you do it this way).

This base URL will be prepended to all URL's.

## Running the Tester

In `package.json`, add a `cypress:open` command to the `scripts` section:

```
  "scripts": {
    "start": "react-scripts start",
    "build": "react-scripts build",
    "test": "react-scripts test",
    "eject": "react-scripts eject",
    "cypress:open": "cypress open"
  },
```

From then on:

```
npm run cypress:open
```

## Selectors

I generally prefer not to "pollute" the front-end components with attributes they otherwise ordinarily wouldn't have,
here that means not adding a whole bunch of "data-" attributes that are used only for testing.

On the other hand, adding all those data attributes can make selecting components much simpler when writing the
integration tests.

The approach used at the present time is _not_ to use such data attributes. This may lead to some convoluted selectors.

This may be revisited.

## Hooks

Use before() to run a function once for the test being described.

Use beforeEach() to run a function for each test case within the described section.

## Using Application Code

These tests use the `authenticationService` from the application code to perform programmatic logins.

The login UI is only tested in the tests for the `LoginPage`, for other components a programmatic login is used.

Using the existing authentication service from the application makes sure that everything for the JSON Web Token is set
up correctly as though we were running the real application.

## Example Test Run

A short video clip showing the tests for this project running is [here](https://youtu.be/KscvXW8dAMs).
