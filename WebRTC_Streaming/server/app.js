var WebSocketServer = require('ws').Server
  , wss = new WebSocketServer({ port: 8888 });

wss.on('connection', function(ws) {
    ws.on('message', function(message) {
        wss.broadcast(message);
    });
});

wss.broadcast = function(data) {
    for(var i in this.clients)
        this.clients[i].send(data);
};
