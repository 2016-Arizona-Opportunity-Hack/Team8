// app/models/user.js
// load the things we need
// var bcrypt   = require('bcrypt-nodejs');


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
    'facebook':{
      'id': '',
      'token': '',
      'email': '',
      'name': ''
    },
    'rangerID' : Math.random() * 1000
  }
  }

}
module.exports = new Users()
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
