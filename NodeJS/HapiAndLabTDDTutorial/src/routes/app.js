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