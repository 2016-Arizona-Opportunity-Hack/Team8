// This code loads and populate profile.html

var dummyJSON = '{"total_miles": 0.0, "team_name": "", "last_name": "lastName", "first_name": "firstName", "ID": "ID123"}'
var profileName = 'NA' // Need to get it from FB

function getData(profileName, callback){
    // To be used later for API call
    // callServer('profile', data, callback)
    callback({'data':JSON.parse(dummyJSON), 'status':200})
}

function populatePage(data){
  if(data.status == 200){
    populateFields(data.data)
    $('h1').html(data.data.first_name + ' ' + data.data.last_name)
  }

}

function requestResponse(data){
  console.log('server responded with:'+ data.status);
}

function logMiles(){
    miles = $('#logMiles p input').val()
    activity = $('#logMiles p select').val()
    callServer('logmiles', {'miles':miles, 'activity': activity}, requestResponse)
}

getData(profileName, populatePage)
