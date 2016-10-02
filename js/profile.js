// This code loads and populate profile.html

var dummyJSON = '{"total_miles": 0.0, "team_name": "TiredFew", "last_name": "Doe", "first_name": "John", "ID": "TiredFew"}'
var profileName = 'NA' // need form FB

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

// Function definition ends

getData(profileName, populatePage)

var ctx = document.getElementById("canvas");
var data = {
    labels: ["02-10", "02-11", "02-12", "02-13", "02-14", "02-15", "02-16"],
    datasets: [
        {
            label: "Activity",
            fill: true,
            lineTension: 0.1,
            backgroundColor: "rgba(75,192,192,0.4)",
            borderColor: "rgba(75,192,192,1)",
            borderCapStyle: 'butt',
            borderDash: [],
            borderDashOffset: 0.0,
            borderJoinStyle: 'miter',
            pointBorderColor: "rgba(75,192,192,1)",
            pointBackgroundColor: "#fff",
            pointBorderWidth: 1,
            pointHoverRadius: 5,
            pointHoverBackgroundColor: "rgba(75,192,192,1)",
            pointHoverBorderColor: "rgba(220,220,220,1)",
            pointHoverBorderWidth: 2,
            pointRadius: 1,
            pointHitRadius: 10,
            data: [65, 59, 80, 81, 56, 55, 40], // data from service
            spanGaps: false,
        }
    ]
};
var myLineChart = new Chart(ctx, {
    type: 'line',
    data: data
});
