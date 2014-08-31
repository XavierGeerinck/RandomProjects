var mailModule      = require('../modules/mail');

var moduleFactory = (function() {
    var modules = {};

    return {
        getModuleApi: function(moduleName, callback) {
            if (modules[moduleName]) {
                return callback(null, new modules[moduleName]());
            }

            return callback('errors.module.api_not_found');
        },

        registerModule: function(moduleName, moduleAPI) {
            modules[moduleName] = moduleAPI;
            return moduleFactory;
        }
    };
})();

moduleFactory.registerModule("mail", mailModule);

module.exports = moduleFactory;
