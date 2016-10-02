// This code loads and populate profile.html

var dummyJSON = '{"total_miles": 0.0, "team_name": "", "last_name": "lastName", "first_name": "firstName", "ID": "ID123"}'
var profileName = 'NA' // Need to get it from FB

function getData(profileName, callback){
    // To be used later for API call
    // callServer('profile', data, callback)
    callback(JSON.parse(dummyJSON))
}

function populatePage(data){
  populateFields(data)
  $('h1').html(data.first_name + ' ' + data.last_name)
}


getData(profileName, populatePage)
