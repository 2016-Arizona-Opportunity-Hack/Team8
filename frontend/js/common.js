var first_name,
    last_name,
    team_name,
    miles,
    team_miles

function populateFields(data){
  if (data.length == 4){
    first_name = data.first_name
    last_name = data.last_name
    team_name = data.team_name
    miles = data.miles
  } else {
    team_miles = data.team_miles
  }
}
