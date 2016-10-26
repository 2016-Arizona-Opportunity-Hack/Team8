// app/models/user.js
// load the things we need
// var bcrypt   = require('bcrypt-nodejs');

<<<<<<< HEAD
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
=======

var Users = function (){
this.users = []
this.save = function(u){
  this.users.push(u)
}
this.findById = function (id){
  for (var i =0 ; i<this.users.length; i++){
    if (this.users[i].rangerID == id){
      return this.users[i]
    }
  }
  return {
      'id': '',
      'token': '',
      'email': '',
      'name': '',
      'rangerID' : Math.random() * 1000
  }
  }

>>>>>>> 7c5104c206abeadc0923066a04d23cbe3f36a6cb
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
