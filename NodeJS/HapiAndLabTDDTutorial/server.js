var Hapi = require('hapi');

exports.start = function(ip, port, options, callback) {
    options = options || {};
    
    var server = new Hapi.createServer(ip, port, options);
    
    // Start the server
    server.start(function() {
        callback();    
    });
};