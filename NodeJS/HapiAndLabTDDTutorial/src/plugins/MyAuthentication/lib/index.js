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