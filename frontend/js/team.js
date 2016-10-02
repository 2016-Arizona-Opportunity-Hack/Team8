// This code loads and populate profile.html

var dummyJSON = '{"team_name": "TiredFew", "members": [{"total_miles": 100.0, "team_name": "TiredFew", "last_name": "Doe", "first_name": "John", "ID": "TiredFew"},\
                                                       {"total_miles": 150.0, "team_name": "TiredFew", "last_name": "Doe", "first_name": "Jane", "ID": "TiredFew"}], "team_miles": 0.0}'
function populatePageTeam(data){
  if(data.status == 200){
    populateFields(data.data)
    $('h1').html(data.data.team_name)
  }
}

load_page()
getData(userData.team_name, populatePageTeam)

function getNames(arr){
  names = []
  for (var i = 0; i < arr.length; i++){
    names.push(arr[i].first_name + arr[i].last_name)
  }
  return names
}

function getMiles(arr){
  miles = []
  for (var i = 0; i < arr.length; i++){
    miles.push(arr[i].total_miles)
  }
  return miles
}

var ctx = document.getElementById("canvas");
names = getNames(teamData.members)
miles = getMiles(teamData.members)
console.log(miles)
var myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: names,
        datasets: [{
            label: '# of Miles',
            data: miles,
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255,99,132,1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true
                }
            }]
        }
    }
});
