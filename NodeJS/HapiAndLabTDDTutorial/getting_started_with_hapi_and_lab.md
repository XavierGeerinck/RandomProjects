# Getting Started With Hapi And Lab
## Requirements
* Node.JS
* NPM

## Setting up our project
### Package.json
As you may know already, node.js has a package manager called NPM, this will help you installing and updating the libraries that you find in the npm library ([https://www.npmjs.org/](https://www.npmjs.org/)) .

To initialize this file, go to your commandprompt and run `npm init`, now fill in all the details that it asks. After you done this you will now see a file called package.json with contents such as this example:

```javascript
{
  "name": "HapiAndLabTDDTutorial",
  "version": "0.1.0",
  "description": "Tutorial",
  "main": "index.js",
  "scripts": {
    "test": "lab"
  },
  "author": "Xavier Geerinck <thebillkidy@gmail.com>",
  "license": "BSD"
}
```

### Adding Hapi, Lab and our other libraries to package.json
We created the package.json and now it is time to add our libraries to it. This can be done by running these simple commands:

* npm install hapi --save
* npm install lab --save
* npm install sinon --save
* npm install supertest --save

When we go look into our package.json, we will see that there is an extra key `dependencies` with the content:

```javascript
"dependencies": {
    "hapi": "~3.0.2",
    "lab": "~2.0.2",
    "sinon": "~1.9.0",
    "supertest": "~0.10.0"
}
```

### Creating our directory structure
We now add the directories __src__ and __test__, the src folder will hold our source files and the test folder our test files. We also add the file index.js, this will be explained in __Creating the server__ . We also add the file MakeFile so that we can run our tests by just running `make test`.

When we look to our directory structure it looks like this:

```
.
├── package.json
├── index.js
├── Makefile
├── src/
|   ├── controllers/
|   └── models/
└── test/
```

### Setting up MakeFile
MakeFile will allow us to run tests by just running `make test` or `make test-cov`, to enable this just put the following content in the Makefile:

```
test:
	@node ./node_modules/.bin/lab ./test/ -r console
test-cov:
	@node ./node_modules/.bin/lab ./test/ -r coverage
    
.PHONY: test
```

This will add the commands `make test` and `make test-cov`, `make test` just runs the tests in the console and `make test-cov` will run the tests with html coverage.

## Creating the server
### BDD Explained
BDD Stands for __Behaviour Driven Development__, BDD is an extension of TDD because it allows us to write test cases which are also readable by non-programmers.

The cycle for developing tests is:

1. Write test
2. Run test and ensure that it fails
3. Fix the test
4. Run test and ensure that it succeeds
5. Repeat from step 1

### Writing our server
Our `index.js` file has just a few lines which will start our server. For this example we configured it so that the server will bind to ip __127.0.0.1__ and port __8000__.

```javascript
// LIBRARIES
var Hapi = require('Hapi');

// CONFIGURATION
var ip = "127.0.0.1";
var port = 8000;
var options = {};

// Create the server object
var server = require('./server');

// Start the server
server.start(ip, port, options, function() {
    console.log('server started');   
});
```

Note that it requires a server object from our directory, this is because we also create a `server.js` file which is going to start our Hapi server, we do this so we got a nice abstract way to touch the server and so that we can test everything.

The `server.js` file contains the following lines:

```javascript
var Hapi = require('hapi');

// ROUTES
var V1AppAPI = require('./src/routes/app');

exports.start = function(ip, port, options, callback) {
    options = options || {};
    
    var server = new Hapi.createServer(ip, port, options);
    
    // Add our routes to the server
    V1AppAPI.routes(server);
    
    // Start the server
    server.start(function() {
        callback();    
    });
};
```

Now we are able to start programming and getting our first route to work.

## Adding our first route
Hapi provides methods to create routes in their seperate files. We put those files in the routes directory.

### GET /app
#### Writing the test
Because we want to be 100% sure of the result each time and we are using BDD we are going to write a test first.

So we create a file called `appController.js` in our test directory with the following content:

```javascript
// Includes
var server  = require('../server');
var request = require('supertest');
var Lab     = require('lab');

// BDD Test utilities
var describe = Lab.experiment;
var it = Lab.test;
var expect = Lab.expect;
var before = Lab.before;
var after = Lab.after;
var beforeEach = Lab.beforeEach;
var afterEach = Lab.afterEach;

// Configuration
var port    = 8000;
var api_url = 'http://127.0.0.1:' + port;

describe('App API', function() {
    before(function(done) {
        // Start Server
        server.start(ip, port, {}, function (err) {
            done();
        });
    });
    
    describe('[GET /app] Get App', function() { 
        it('should return { "message": "Hello World" } on success.', function(done) {
            request(api_url)
                .get('/app')
                .set('Content-Type', 'application/json; charset=utf-8')
                .end(function (err, res) {
                    if (err) throw err;
                    expect(res.statusCode).to.equal(200);
                    expect(res.body).to.have.property('message');
                    expect(res.body.message).to.equal("Hello World");
                    done();
                });
        });
    });
});
```

This content says that we are going to load our libraries and configuration, then before we start running our tests we will first start the server.

Then when the server is started we are going to call the main url located at http://127.0.0.1:8080 and there the path /app.

When we did this, we expect it to have statusCode 200 which equals to OK and the content { message: "Hello World" }


#### Controller
We will create a controller which is going to process the request that we get from as soon as someone called the route.

This is why we create the file `app.js` in the folder `src/controllers/`. We not put the following content into it:

```javascript
exports.getHelloWorld = function(request, reply) {
	reply({ "message": "Helo World" });
};
```

Every route has the parameters request and reply 

__request__ contains all the request variables such as the authentication used, route configuration used, headers used, .... 

__reply__ is the callback that we use to send the content back that we want to show to the user. In this method we configured it so it will give { "message": "Hello World" } back to the client.

#### Map the path to the controller with a route
When we created the controller we will write a route that will call this method in the controller from as soon as the required path, method and config is met.

Create the file called `app.js` in the `routes` directory.

Now put this content into that file:

```javascript
var Types  = require('hapi').types;
var app    = require('../controllers/app');

var routes = [
	{
        method: 'GET',
        path: '/app',
        config: {
            handler: app.getHelloWorld
        }
    }
];

module.exports.routes = function (server) {
    server.route(routes);
};
```

This will check every request that comes in, then filter it on the method, path and config and calls the specified handler from as soon as every requirement is met.

#### Configuring the routes in the server
We now have one last thing to do before we can actually use this route in our server. We need to configure our server so that it can find the routes.

To do this we go back to our `index.js` file and we add the declaration of the variable V1AppRoutes on the top: `
var V1AppAPI = require('./src/routes/app');`

We also say that the server should call the routes:
`V1AppAPI(server);`, we do this just before starting the server.

If you did all this you will have the following content in `index.js`:

```javascript
// LIBRARIES
var Hapi = require('Hapi');

// ROUTES
var V1AppAPI = require('./src/routes/app');

// CONFIGURATION
var ip = "127.0.0.1";
var port = 8000;

// Create the server object
var server = new Hapi.Server(ip, port, {});

// Add our routes to the server
V1AppAPI.routes(server);

// Start the server
server.start(function() {
    console.log('server started');   
});
```

## Adding our own Authentication Plugin
## Creating routes
### GET
### POST
### PUT
### DELETE