# Getting Started With Hapi And Lab
## Setting up our project
### Package.json
As you may know already, node.js has a package manager called NPM, this will help you installing and updating the libraries that you find in the npm library ([https://www.npmjs.org/](https://www.npmjs.org/)) .

To initialize this file, go to your commandprompt and run ```npm init```, now fill in all the details that it asks. After you done this you will now see a file called package.json with contents such as this example:

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


### Creating our directory structure
We now add the directories __src__ and __test__, the src folder will hold our source files and the test folder our test files. We also add the files index.js and server.js, these will be explained in __Creating the server__ . We also add the file MakeFile so that we can run our tests by just running make test.

When we look to our directory structure it looks like this:

```
.
├── package.json
├── index.js
├── server.js
├── Makefile
├── src/
|   ├── controllers/
|   └── models/
└── test/
```

### Setting up MakeFile
MakeFile will allow us to run tests by just running ```make test``` or ```make test-cov```, to enable this just put the following content in the Makefile:

```
test:
	@node ./node_modules/.bin/lab ./test/ -r console
test-cov:
	@node ./node_modules/.bin/lab ./test/ -r coverage
    
.PHONY: test
```

This will add the commands ```make test``` and ```make test-cov```, make test just runs the tests in the console and make test-cov will run the tests with html coverage.

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
We start by creating our test, therefor create the file ```server.js``` in the test directory.

Now we put this content in that file:

```javascript

```



## Adding our own Authentication Plugin
## Creating routes
### GET
### POST
### PUT
### DELETE