// app/models/user.js
// load the things we need
// var bcrypt   = require('bcrypt-nodejs');

module.exports = function() {
    this.facebook = {
        id           : "",
        token        : "",
        email        : "",
        name         : ""
    },
    this.twitter = {
        id           : "",
        token        : "",
        displayName  : "",
        username     : ""
    },
    this.google = {
        id           : "",
        token        : "",
        email        : "",
        name         : ""
    },
    this.rangerID = "12345",
    this.totalMiles =  0,
    this.name = "",
    this.teamName = ""
    return this;
}
// methods ======================
// generating a hash
// userSchema.methods.generateHash = function(password) {
//     return bcrypt.hashSync(password, bcrypt.genSaltSync(8), null);
// };
//
// // checking if password is valid
// userSchema.methods.validPassword = function(password) {
//     return bcrypt.compareSync(password, this.local.password);
// };
//
// // create the model for users and expose it to our app
// module.exports = mongoose.model('User', userSchema);
