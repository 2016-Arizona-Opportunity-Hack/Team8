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

var ctx = document.getElementById("canvas");
var data = {
    labels: ["You"],
    datasets: [
        {
            backgroundColor: [
                'rgba(253,195,14, 0.2)'
            ],
            borderColor: [
                'rgba(253,195,25,1)'
            ],
            borderWidth: 1,
            data: [400]
        }
    ]
};
var canvas = new Chart(ctx, {
    type: 'horizontalBar',
    data: data,
    options: {
            scales: {
                xAxes: [{
                    display: true,
                    ticks: {
                        beginAtZero: true,    // minimum will be 0, unless there is a lower value.
                        max: 565,
                    }
                }]
            },
        responsive: true,
        legend: {
            display: false
        }
    }
});
