var first_name,
    last_name,
    team_name,
    miles,
    team_miles

function populateFields(data){
  console.log(data)
  if (Object.keys(data).length == 5){
    first_name = data.first_name
    last_name = data.last_name
    team_name = data.team_name
    miles = data.miles
  } else {
    team_miles = data.team_miles
  }
}

function getData(profileName, callback){
    // To be used later for API call
    // callServer('profile', data, callback)
    callback({'data':JSON.parse(dummyJSON), 'status':200})
}
