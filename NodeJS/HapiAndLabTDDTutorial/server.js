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