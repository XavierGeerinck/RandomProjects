// LIBRARIES
var Hapi = require('Hapi');

// CONFIGURATION
var ip = "0.0.0.0";
var port = 8000;
var options = {};

// Create the server object
var server = require('./server');

// Start the server
server.start(ip, port, options, function() {
    console.log('server started');   
});
