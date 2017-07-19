var net = require('net');
var fs = require('fs');
var arDrone = require('..');

var speed = 0.05;
var client = arDrone.createClient();
var markerFound = false; //ako nade marker prestaje se kretati i ostaje na lokaciji x,y

var arrayIndex = 0;
var array = fs.readFileSync('coordinates.txt').toString().split("\n");
var currentDestinationLatitude = array[arrayIndex];   //0
var currentDestinationLongitude = array[++arrayIndex];//1

client.config('general:navdata_options', 777060865); //enable gps
client.config('control:altitude_max', 3000); //najvise 3 metra
	
client.after(2500,function(){
	//client.calibrate(0);
});

	
//client.after(4000,function(){
	//console.log("FRONT");
	//client.front(0.3);
//});	

net.createServer(function (socket) {

  socket.setEncoding('utf8');
  console.log("Listening on port 5000");	
  socket.on('data', function (data) {
	  
		//ukloni CRLF s kraja stringa
		data = data.trim();
		console.log(data);
		switch(data){
			case 'drone_upleft':			
				client.front(speed);
				client.left(speed);
				break;
			case 'drone_upright': 			
				client.front(speed);
				client.right(speed);
				break;
			case 'drone_downleft': 			
				client.back(speed);
				client.left(speed);
				break;
			case 'drone_downright':							
				client.back(speed);
				client.right(speed);
				break;
			case 'drone_center':
				console.log("STOP");
				client.stop();
				break;
		}
		if (data == "drone_takeoff"){
			client.takeoff();
		}
		if (data == "drone_stop"){
			client.stop();
		}
		if (data == "drone_land"){
			client.land();
		}
		if (data == "drone_found"){
			markerFound = true;
		}
		if (data == "drone_carFound"){
			client.land();
		}
  });
	
}).listen(5000);


client.after(100,function(){
	//sigurnosnih 100 milisekundi cisto da priceka prije nego sto se dron kalibrira
	console.log("Listening to navdata");

	this.on('navdata', function(data){
		//console.log(data.demo);
		
		//ako je marker pronaden prestajemo se kretati po koordinatama
		if (!markerFound){
			//ako smo dosli na koordinate x,y reci mu da ide na sljedece
			if (currentDestinationLatitude == latitude && currentDestinationLongitude == longitude){ 
				if (arrayIndex != array.length-1){
					currentDestinationLatitude = array[++arrayIndex];
					currentDestinationLongitude = array[++arrayIndex];					
				}
				else{
					//nalazimo se na zadnjim koordinatama
				}				
			}
			
			if (typeof data.magneto != 'undefined' && data.magneto){
				console.log(data.magneto.heading.fusionUnwrapped);
				//check for drone height
				if (data.demo.altitude >= 2){
				 client.land();
				 client.stop();
				}
				//if not oriented approx at 0 degrees
				//tu negdi ide i client.front(0.7)
				if (data.magneto.heading.fusionUnwrapped >= 5){
					//client.clockwise(-1);
				}
				else if (data.magneto.heading.fusionUnwrapped <= -5){
					//client.clockwise(1);
				}
				else{
					client.stop();
				}
			}
		}
	});
});
client.after(6000,function(){
	client.land();
});

