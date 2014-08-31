var util = require('util');
var BaseModule = require('./baseModule');

function Mail() {
};

util.inherits(Mail, BaseModule);

Mail.prototype.registerJob = function(scheduler, callback) {
    var RecurrenceRule = scheduler.RecurrenceRule;
    var rule = new RecurrenceRule();
    rule.second = 5;

    scheduler.scheduleJob(rule, function() {
        var data = "You have 5 new emails.";
        callback(null, data);
    });
};

module.exports = Mail;
