var Hapi = require('hapi');
var server = new Hapi.Server(3000);
var io = require('socket.io')(8000); // Socket.io on port 8000
var moduleFactory = require('./abstract_factories/module');
var schedule = require('node-schedule');

io.on('connection', function(socket) {
    console.log('A user connected');

    moduleFactory.getModuleApi("mail", function(err, moduleApi) {
        moduleApi.registerJob(schedule, function(err, response) {
            socket.emit('notification', { msg: response });
        });
    });

    socket.on('disconnect', function() {
        console.log('User disconnected');
    });
});

server.start(function () {
    console.log('REST Server running at:', server.info.uri);
});
