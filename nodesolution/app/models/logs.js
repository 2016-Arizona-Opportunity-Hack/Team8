// load the things we need
var mongoose = require('mongoose');

var logMile = mongoose.Schema({
    google           : {
        Miles        : Number, 
        email        : String,
        activity     : String,
        date         : String
    }
});


// create the model for users and expose it to our app
module.exports = mongoose.model('Logs', logMile)
