// config/passport.js

// load all the things we need
var LocalStrategy   = require('passport-local').Strategy;
var FacebookStrategy = require('passport-facebook').Strategy;
var configAuth = require('./auth');
var Users = require('../app/models/user');
// expose this function to our app using module.exports
module.exports = function(passport) {

	// =========================================================================
    // passport session setup ==================================================
    // =========================================================================
    // required for persistent login sessions
    // passport needs ability to serialize and unserialize users out of session

    // used to serialize the user for the session
    passport.serializeUser(function(user, done) {
        done(null, user.rangerID);
    });

    // used to deserialize the user
    passport.deserializeUser(function(id, done) {
      // TODO: Implement searching algorithm to return correct user based on ID
        user = Users.findById(id);
        done(null, user);

    });

    // =========================================================================
    // FACEBOOK LOGIN ==========================================================
    // =========================================================================
    passport.use('facebook', new FacebookStrategy({
      clientID        : configAuth.facebookAuth.clientID,
      clientSecret    : configAuth.facebookAuth.clientSecret,
      callbackURL     : configAuth.facebookAuth.callbackURL,
      profileFields: ['id', 'displayName', 'email']
    },
    function(token, refreshToken, profile, done){
      process.nextTick(function(){
        var user = {
              'id'           : profile.id,
              'token'        : token,
              'email'        : profile.emails[0].value,
              'name'         : profile.displayName,
              'rangerID' : Math.random() * 1000
        }
        Users.save(user)
        return done(null, user);
      });
    }))
};
