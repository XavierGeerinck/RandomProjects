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