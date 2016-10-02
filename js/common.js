var userData = {'first_name':'','last_name':'','team_name':'','miles':''}
var teamData = {'team_name':'', 'team_miles': 0.0, 'members':[]}

function populateFields(data){
  // This is for populating profile information
  if (Object.keys(data).length == 5){
    userData.first_name = data.first_name
    userData.last_name = data.last_name
    userData. team_name = data.team_name
    userData.miles = data.miles
    localStorage.setItem('userData', JSON.stringify(userData));
  } else { //Information from the team get call
    teamData.team_name = data.team_name
    teamData.team_miles = data.team_miles
    teamData.members = data.members
    localStorage.setItem('teamData', JSON.stringify(teamData));
  }
}

function load_page(){
  if (localStorage.getItem("userData") != null) {
    userData = JSON.parse(localStorage.getItem("userData"))
  }
}

function getData(params, callback){
    // To be used later for API call
    // callServer('team', params, callback)
    callback({'data':JSON.parse(dummyJSON), 'status':200})
}
