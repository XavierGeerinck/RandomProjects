// Libraries
var Hapi = require('hapi');
var Lab = require('lab');

// BDD Test utilities
var describe = Lab.experiment;
var it = Lab.test;
var expect = Lab.expect;
var before = Lab.before;
var after = Lab.after;
var beforeEach = Lab.beforeEach;
var afterEach = Lab.afterEach;

// Sinon
var sinon = require('sinon');

// Our files
var server = require('../server');

describe('Server', function () {
//    var fakeServer;
//    
//    before(function(done) {
//        fakeServer = {
//            start: function(ip, port, options, callback) { callback(); }
//        };
//        
//        sinon.stub(Hapi, 'createServer').yields(fakeServer);
//        
//        done();
//    });
//    
//    it('Start the server', function(done) {
//        // Configure the server to run on the following port and ip
//        var port = 8000;
//        var ip = "127.0.0.1";
//        var options = {};
//        
//        // Start the server
//        server.start(ip, port, options, function(err) {
//            serverStub.once().withArgs(ip, port, options);    
//            serverStub.once().withArgs(function() {});
//            done();
//        });
//    });
//    
//    after(function(done) {
//        Hapi.createServer.restore();
//        done();    
//    });
});