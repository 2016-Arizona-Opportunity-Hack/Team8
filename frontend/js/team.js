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

var barOptions_stacked = {
    tooltips: {
        enabled: true
    },
    scales: {
        xAxes: [{
            ticks: {
                beginAtZero:true,
                fontFamily: "'Open Sans Bold', sans-serif",
                fontSize:11
            },
            scaleLabel:{
                display:true
            },
            gridLines: {
            },
            stacked: true
        }],
        yAxes: [{
            gridLines: {
                display:false,
                color: "#fff",
                zeroLineColor: "#fff",
                zeroLineWidth: 0
            },
            ticks: {
                fontFamily: "'Open Sans Bold', sans-serif",
                fontSize:11
            },
            stacked: true
        }]
    },
    legend:{
        display:false
    },

    pointLabelFontFamily : "Quadon Extra Bold",
    scaleFontFamily : "Quadon Extra Bold",
};

var ctx = document.getElementById("Chart1");
var myChart = new Chart(ctx, {
    type: 'horizontalBar',
    data: {
        labels: ["runs"],
        datasets: [{
            data: miles,
            backgroundColor: "rgba(63,103,126,1)",
            hoverBackgroundColor: "rgba(50,90,100,1)"
        }]
    },

    options: barOptions_stacked,
});
