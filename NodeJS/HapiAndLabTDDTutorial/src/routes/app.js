var Types  = require('hapi').types;
var app    = require('../controllers/app');

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
    },
    {
        method: 'GET',
        path: '/secure',
        config: {
            handler: app.getSecure,
            auth: 'MyAuthenticationScheme'
        }
    }
];

module.exports.routes = function (server) {
    server.route(routes);
};