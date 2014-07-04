# Creating a BDD Server with Hapi and Lab
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
* npm install boom --save
* npm install hoek --save

When we go look into our package.json, we will see that there is an extra key `dependencies` with the content:

```javascript
"dependencies": {
    "hapi": "~3.0.2",
    "lab": "~2.0.2",
    "sinon": "~1.9.0",
    "supertest": "~0.10.0",
    "boom": "~2.3.0",
    "hoek": "~1.5.2"
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
var ip      = "0.0.0.0";
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

This is why we create the file `app.js` in the folder `src/controllers/`. We now put the following content into it:

```javascript
exports.getHelloWorld = function(request, reply) {
	reply({ "message": "Hello World" });
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

### GET /app/{name}
Hapi also allows us to accept path parameters, these are annotated by {PARAMNAME} (In our example request.params.name)

#### Writing the test
Our test will be almost the same as the one for the __Hello World__ example, only this time we will replace world by our name. So add a new it in the describe('[GET /app] Get App') with this content:

```javascript
it('should return { "message": "Hello World" } on success.', function(done) {
    request(api_url)
        .get('/app/Xavier')
        .set('Content-Type', 'application/json; charset=utf-8')
        .end(function (err, res) {
            if (err) throw err;
            expect(res.statusCode).to.equal(200);
            expect(res.body).to.have.property('message');
            expect(res.body.message).to.equal("Hello Xavier");
            done();
        });
});
```

When we run `make test` we will see that it will output this error:

```
  .x

 1 of 2 tests failed:

 2) App API [GET /app] Get App should return { "message": "Hello World" } on success.:

      actual expected

      24004
```

Which says that it gives a 404 error, this is of course because we still have to write the controller and the routes. Lets do that now.
#### Controller
For the controller, add an extra exports.getHelloByName as so:
```javascript
exports.getHelloByName = function(request, reply) {
	reply({ "message": "Hello " + request.params.name });
};
```

#### Configuring the routes in the server
And for the route we will almost do the same, only this time we will add {} for our name parameter.

```javascript
var routes = [
	{
        method: 'GET',
        path: '/app',
        config: {
            handler: app.getHelloWorld
        }
    },
    {
        method: 'GET',
        path: '/app/{name}',
        config: {
            handler: app.getHelloByName
        }
    }
];
```

When we re-run `make test` now we will see that all tests succeed.

### GET /app2?name=NAMEHERE
If you would like to add name as a query parameter you can do this to (Note: We used app2 since we already have GET /app)

#### Writing the test
```javascript
it('should return { "message": "Hello World" } on success.', function(done) {
    request(api_url)
        .get('/app2?name=Xavier')
        .set('Content-Type', 'application/json; charset=utf-8')
        .end(function (err, res) {
            if (err) throw err;
            expect(res.statusCode).to.equal(200);
            expect(res.body).to.have.property('message');
            expect(res.body.message).to.equal("Hello Xavier");
            done();
        });
});
```

Which says that it gives a 404 error, this is of course because we still have to write the controller and the routes. Lets do that now.
#### Controller
For the controller, add an extra exports.getHelloByName as so:
```javascript
exports.getHelloByQueryName = function(request, reply) {
	reply({ "message": "Hello " + request.query.name });
};
```

#### Configuring the routes in the server
And for the route we will almost do the same, only this time we will add {} for our name parameter.

```javascript
var routes = [
	{
        method: 'GET',
        path: '/app',
        config: {
            handler: app.getHelloWorld
        }
    },
    {
        method: 'GET',
        path: '/app/{name}',
        config: {
            handler: app.getHelloByName
        }
    },
    {
        method: 'GET',
        path: '/app2',
        config: {
            handler: app.getHelloByQueryName,
            validate: {
                query: {
                    name: Types.String()  
                }
            }
        }
    }
];
```

When we re-run `make test` now we will see that all tests succeed.

### POST /app, Payload: Name
Posts are almost exactly the same as sending a GET with a path parameter or query parameter, only this time we use `request.payload.name`.

#### Writing the test
```javascript
it('should return { "message": "Hello Xavier" } on success.', function(done) {
    request(api_url)
        .post('/app')
        .send({ name: 'Xavier' })
        .set('Content-Type', 'application/json; charset=utf-8')
        .end(function (err, res) {
            if (err) throw err;
            expect(res.statusCode).to.equal(200);
            expect(res.body).to.have.property('message');
            expect(res.body.message).to.equal("Hello Xavier");
            done();
        });
});
```

Which says that it gives a 404 error, this is of course because we still have to write the controller and the routes. Lets do that now.
#### Controller
```javascript
exports.postHelloByName = function(request, reply) {
	reply({ "message": "Hello " + request.payload.name });
};
```

#### Configuring the routes in the server
```javascript
var routes = [
	{
        method: 'GET',
        path: '/app',
        config: {
            handler: app.getHelloWorld
        }
    },
    {
        method: 'GET',
        path: '/app/{name}',
        config: {
            handler: app.getHelloByName
        }
    },
    {
        method: 'POST',
        path: '/app',
        config: {
            handler: app.postHelloByName,
            validate: {
                payload: {
                    name: Types.String()  
                }
            }
        }
    },
    {
        method: 'GET',
        path: '/app2',
        config: {
            handler: app.getHelloByQueryName,
            validate: {
                query: {
                    name: Types.String()  
                }
            }
        }
    }
];
```

## Adding our own Authentication Plugin
A lot of people user a username and password to sign in, however when you are using a API that requires authentication they mostly trade these credentials for a __token__ to sign in with.

We can create our own authentication module for Hapi which is going to use this plugin to authenticate a user when they got a token provided. We can also then use this authentication to protect different routes.

### Directory Structure
We can put our plugin in a brand new directory, then push it to npm and use npm install, our we can keep it private and put it under a plugins directory in our src folder. We choose for the second option for this tutorial.

Create a plugins directory under the src folder, in there create another directory called __MyAuthentication__.

In that directory, run `npm init` to again init a new package.json.

On the end you will need something like this:

```
.
├── package.json
├── index.js
└── lib/
    └── index.js
```

The root index.js file will just have this content:
```javascript
module.exports = require('./lib');
```

### Creating the plugin
Hapi let's us __register__ plugins and use different implementation schemes for the authentication.

This is why we create a __register__ and a __implementation__ function which are going to register our plugin and provide the implementation.

```javascript
// Load Modules
var Hapi         = require('hapi');
var Boom         = require('boom');
var Hoek         = require('hoek');

// Shortcuts
var Utils = Hapi.utils;

// Declare vars
var internals = {};
var defaultOptions = {
    users: {
		"TokenA": {
			username: "UserA",
			password: "PasswordA"
		},
		"TokenB": {
			username: "UserB",
			password: "PasswordB"
		}
	}
};

// Plugin registration
exports.register = function (plugin, options, next) {
    internals.options = options || defaultOptions;
    
    // Validate Credentials
    var loadUser = function(token, callback) {
    	var user = _checkToken(token);
    	
    	if (user) {
    		return callback(null, true, user);	
    	}
    	
    	return callback(null, false);
    };
    
    plugin.auth.scheme('MyAuthenticationScheme', internals.implementation);
    plugin.auth.strategy('MyAuthenticationScheme', 'MyAuthenticationScheme', { validateFunc: loadUser });
    
    return next();
};

// Implementation
internals.implementation = function (server, options) {
    // Assert
    Utils.assert(server, 'Server is required');
    
    var settings = Hoek.clone(options);

    var scheme = {
        authenticate: function (request, reply) {
            var bearerToken = request.headers.bearer;

            if (!bearerToken) {
                return reply(Boom.badRequest(JSON.stringify({ "message": "unauthorized" }), 'MyAuthenticationScheme'));
            }
    
            // Check if the token is valid, get the user back
            settings.validateFunc(bearerToken, function (err, isValid, user) {
                // Check if credentials are valid
                if (err || !isValid || !user) {
                    return reply(Boom.unauthorized(JSON.stringify({ "message": "unauthorized" }), 'feedient'), { credentials: bearerToken });
                }
                
                // Return the user
                return reply(null, { credentials: user });
            });       
        }
    };
    
    return scheme;
};

var _checkToken = function(token) {
	var users = internals.options.users;
	
	if (users[token]) {
		return users[token];
	}
	
	return false;
}
```

If you  read the above code you an see that we got 2 users hardcoded. When we match a token with a user, we return true, else we return unauthorized.

### Register Plugin
We can register the plugin by add this line just above the routes adding

```javascript
server.pack.require('./src/plugins/MyAuthentication', { users: {} }, function (err) { });
```

We then get this in server.js:

```javascript
var Hapi = require('hapi');

// ROUTES
var V1AppAPI = require('./src/routes/app');

var authOptions = {
    users: {
        "TokenA": {
            username: "UserA",
            password: "PasswordA"
        },
        "TokenB": {
            username: "UserB",
            password: "PasswordB"
        }
    }
}

exports.start = function(ip, port, options, callback) {
    options = options || {};
    
    var server = new Hapi.createServer(ip, port, options);
    server.pack.require('./src/plugins/MyAuthentication', authOptions, function (err) { });
    
    // Add our routes to the server
    V1AppAPI.routes(server);
    
    // Start the server
    server.start(function() {
        callback();    
    });
};
```

### Adapt Routing to use the new Authentication
To use this authentication and require it we have to add the __auth__ key to the routes under the config key.

Example:
```javascript
{
    method: 'GET',
    path: '/app2',
    config: {
        handler: app.getHelloByQueryName,
        auth: 'MyAuthenticationScheme',
        validate: {
            query: {
                name: Types.String()  
            }
        }
    }
}
```
### Full Controller Example
#### Test
```javascript
it('should return { "message": "authenticated" } on success', function(done) {
    request(api_url)
        .get('/secure')
        .set('Bearer', 'TokenA')
        .set('Content-Type', 'application/json; charset=utf-8')
        .end(function (err, res) {
            if (err) throw err;
            expect(res.statusCode).to.equal(200);
            expect(res.body).to.have.property('message');
            expect(res.body.message).to.equal("authenticated");
            done();
        });
});
```

And a test to make sure that it fails when the wrong user is used.

```javascript
it('should return { "message": "unauthorized" } on failure', function(done) {
    request(api_url)
        .get('/secure')
        .set('Bearer', 'TokenABC')
        .set('Content-Type', 'application/json; charset=utf-8')
        .end(function (err, res) {
            if (err) throw err;
            expect(res.statusCode).to.equal(401);
            expect(res.body.message).to.equal('{\"message\":\"unauthorized\"}');
            done();
        });
});
```

#### Controller
```javascript
exports.getSecure = function(request, reply) {
    reply({ "message": "authenticated" });
};
```

#### Route
```javascript
{
    method: 'GET',
    path: '/secure',
    config: {
        handler: app.getSecure,
        auth: 'MyAuthenticationScheme'
    }
}
```

## Creating a MongoDB Plugin
The goal of this is that we are able to expose the mongodb object to our server. First we start by creating the same directory structure as the Authentication plugin, this time call the plugin directory __MongoDB__:

```
.
├── package.json
├── index.js
└── lib/
    └── index.js
```

The only thing that is going to change is the content of lib/index.js, this is going to become:

```javascript
var mongodb     = require('mongodb');
var MongoClient = mongodb.MongoClient;

exports.register = function (plugin, options, next) {
    MongoClient.connect(options.url, options.settings || {}, function (err, db) {
        if (err) return next(err);
        plugin.expose('db', db);
        console.log("Connected to: " + options.url + " exposed as: request.server.plugins['MongoDB'].db");
        return next();
    });
};
```

We can now require this in our server.pack, make sure to use a callback since MongoDB takes a while to connect!

```javascript
var Hapi = require('hapi');

// ROUTES
var V1AppAPI = require('./src/routes/app');

var databaseConfig = {
    "url": "mongodb://localhost:27017/somedb",
    "options": {
        "db": {
            "native_parser": false    
        }
    }
};

exports.start = function(ip, port, options, callback) {
    options = options || {};
    
    var server = new Hapi.createServer(ip, port, options);

    server.pack.require('./src/plugins/FeedientMongodb', databaseConfig, function (err) {
        if (err) { throw err; }
        
        // Add our routes to the server
        V1AppAPI.routes(server);

            // Start the server
        server.start(function() {
            callback();    
        });
    });
};
```

Now we can access our MongoDB object from anywhere where we can access the server object.

Example, in the request object on a route:

```javascript
exports.mongoRoute = function(request, reply) {
    var mongoDB = request.server.plugins['MongoDB'].db;
};
```

# Reference Hapi
## Routes
[https://github.com/spumko/hapi/blob/master/docs/Reference.md#serverrouteoptions](https://github.com/spumko/hapi/blob/master/docs/Reference.md#serverrouteoptions)
[https://github.com/spumko/hapi/blob/master/docs/Reference.md#serverrouteroutes](https://github.com/spumko/hapi/blob/master/docs/Reference.md#serverrouteroutes)
## Request / Reply / Response
[https://github.com/spumko/hapi/blob/master/docs/Reference.md#request-object](https://github.com/spumko/hapi/blob/master/docs/Reference.md#request-object)
[https://github.com/spumko/hapi/blob/master/docs/Reference.md#reply-interface](https://github.com/spumko/hapi/blob/master/docs/Reference.md#reply-interface)
[https://github.com/spumko/hapi/blob/master/docs/Reference.md#response-object](https://github.com/spumko/hapi/blob/master/docs/Reference.md#response-object)
## Plugins
### Registering
[https://github.com/spumko/hapi/blob/master/docs/Reference.md#hapipack](https://github.com/spumko/hapi/blob/master/docs/Reference.md#hapipack)
### Authentication Scheme
[https://github.com/spumko/hapi/blob/master/docs/Reference.md#pluginauthschemename-scheme](https://github.com/spumko/hapi/blob/master/docs/Reference.md#pluginauthschemename-scheme)
[https://github.com/spumko/hapi/blob/master/docs/Reference.md#pluginauthstrategyname-scheme-mode-options](https://github.com/spumko/hapi/blob/master/docs/Reference.md#pluginauthstrategyname-scheme-mode-options)
### Exposing variables so that we can use those in our application
[https://github.com/spumko/hapi/blob/master/docs/Reference.md#pluginexposeobj](https://github.com/spumko/hapi/blob/master/docs/Reference.md#pluginexposeobj)