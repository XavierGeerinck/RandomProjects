exports.getHelloWorld = function(request, reply) {
	reply({ "message": "Hello World" });
};

exports.getHelloByName = function(request, reply) {
	reply({ "message": "Hello " + request.params.name });
};

exports.getHelloByQueryName = function(request, reply) {
	reply({ "message": "Hello " + request.query.name });
};

exports.postHelloByName = function(request, reply) {
	reply({ "message": "Hello " + request.payload.name });
};

exports.getSecure = function(request, reply) {
    reply({ "message": "authenticated" });
};