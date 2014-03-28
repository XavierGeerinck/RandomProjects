// Includes
var server  = require('../server');
var request = require('supertest');
var Lab     = require('lab');

// BDD Test utilities
var describe = Lab.experiment;
var it = Lab.test;
var expect = Lab.expect;
var before = Lab.before;
var after = Lab.after;
var beforeEach = Lab.beforeEach;
var afterEach = Lab.afterEach;

// Configuration
var port    = 8000;
var api_url = 'http://127.0.0.1:' + port;

describe('App API', function() {
    before(function(done) {
        // Start Server
        server.start(ip, port, {}, function (err) {
            done();
        });
    });
    
    describe('[GET /app] Get App', function() { 
        it('should return { "message": "Hello World" } on success.', function(done) {
            request(api_url)
                .get('/app')
                .set('Content-Type', 'application/json; charset=utf-8')
                .end(function (err, res) {
                    if (err) throw err;
                    expect(res.statusCode).to.equal(200);
                    expect(res.body).to.have.property('message');
                    expect(res.body.message).to.equal("Hello World");
                    done();
                });
        });
    });
});