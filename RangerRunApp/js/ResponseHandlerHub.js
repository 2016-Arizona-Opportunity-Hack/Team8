// Global variables declarations
var apiEndPoint = 'http://localhost:8080'

// hitting the endpoint
function httpGetAsync(theUrl, callback, rType, parameters){
    $.ajax({
      url: theUrl,
      type: rType,
      dataType: "json",
      data: parameters,
      success: function(result){
            callback(result);
      }
    });
}

//formatting the urlStrings

function formatString(type){
  if (type == 'profile'){
      return apiEndPoint + '/profile';
  } else if (type == 'team'){
      return apiEndPoint + '/team'
  } else if (type == 'login'){
      return apiEndPoint + '/auth/google'
  } else if (type == 'logmiles'){
      return apiEndPoint + '/logmiles'
  }
}

function callServer(type, variables, callback){
    url = formatString(type);
    if(type == 'login'){
      window.location.href = url
    }
    else if(type == 'logMiles'){
        httpGetAsync(url, callback, 'post', variables)
    } else {
        httpGetAsync(url, callback, 'get', variables)
    }

}
