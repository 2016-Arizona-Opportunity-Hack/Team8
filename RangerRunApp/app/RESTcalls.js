var http = require('http');
const REST_PORT = 8081;
const REST_HOST = "localhost"

module.exports = {
  login : function(authType, authObject){

    var postData = JSON.stringify(authObject);
    var options = {
      hostname: 'www.google.com',
      port: REST_PORT,
      hostname: REST_HOST,
      path: '/login',
      method: 'POST',
      headers: {
        'Content-Type': 'application/application/json',
        'auth-type': authType,
        'Content-Length': Buffer.byteLength(postData)
      }
    };
    var user;
    var req = http.request(options, function(res) {
      var body = '';
      res.on('data', function(d){
        body += d;
      });
      res.on('end', function(){
        console.log("Response: " + body);
        user = eval("("+ body + ")");
      });
      res.on('error', function(e){
        console.log(e);
      });
    });
    req.write(postData);

    req.on('error', function(e) {
      console.log("Request error: " + e);
    })
    req.end();
    return user;

  }
}
