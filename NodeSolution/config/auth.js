// config/auth.js

// expose our config directly to our application using module.exports
module.exports = {

    'facebookAuth' : {
        'clientID'        : '556864234515037', // your App ID
        'clientSecret'    : 'de6d173e3bb0299ade915b25b69675ce', // your App Secret
        'callbackURL'     : 'http://localhost:8088/auth/facebook/callback'
    },

    'twitterAuth' : {
        'consumerKey'        : 'your-consumer-key-here',
        'consumerSecret'     : 'your-client-secret-here',
        'callbackURL'        : 'http://localhost:8088/auth/twitter/callback'
    },

    'googleAuth' : {
        'clientID'         : '642172236840-98o3q633iuq0vlum8k3ldcppnjgs2duh.apps.googleusercontent.com',
        'clientSecret'     : '4w0-LByzDyyLIYeK84GfBBLn',
        'callbackURL'      : 'http://localhost:8088/auth/google/callback'
    }
    'apiAuth' : {
      'callbackURL'        : 'http://10.143.186.63:8080/Backend/rest'
    }
};
