// app/models/user.js
// load the things we need
// var bcrypt   = require('bcrypt-nodejs');

module.exports = {
  profile : {
    facebook         : {
        id           : "",
        token        : "",
        email        : "",
        name         : ""
    },
    twitter          : {
        id           : "",
        token        : "",
        displayName  : "",
        username     : ""
    },
    google           : {
        id           : "",
        token        : "",
        email        : "",
        name         : ""
    }

  };
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
