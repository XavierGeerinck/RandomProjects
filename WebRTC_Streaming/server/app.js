var io = require('socket.io')(8888);

io.on('connection', function(socket) {
    console.log('Client Connected');

    socket.on('message', function(data) {
        io.sockets.emit('message', data);
    });
});
