# journey-planner
This is a simple RESTful web service.<br>
It aims to plan journeys composed of single transits between user defined places with subsequent departure and arrival times.<br>
<br>
Currently there are implemented CRUD operations for journey-plan, place and transit lifecycle.<br>
Negative scenarios are partially prevented (in case of journey-plan).
Error messages and transits addition to journey-plan needs still to be implemented.<br>

## To run this application
Type the following commands:
```
cd journey-planner
mvn install
```

## Using web API

<b>Request:</b><br>
POST<br>
`http://localhost:8080/journey-plans`<br>
Parameters:<br>
name - journey plan name<br>
<br>
Creates new journey plan<br>
Response: Journey plan id<br>
For example:
```
3
```


<b>Request:</b><br>
GET<br>
`http://localhost:8080/journey-plans/{id}`<br>
Response: {id} journey plan details<br>
For example:
```
{
  "id": 3,
  "name": "Plan B"
}
```


<b>Request:</b><br>
POST<br>
`http://localhost:8080/journey-plans/{id}`<br>
Parameters:<br>
name - new journey plan name<br>
<br>
Renames {id} journey plan<br>


<b>Request:</b><br>
DELETE<br>
`http://localhost:8080/journey-plans/{id}`<br>
Removes {id} journey plan<br>


<b>Request:</b><br>
POST<br>
`http://localhost:8080/places`<br>
Parameters:<br>
name - place name<br>
<br>
Creates new place<br>
Response: Place id<br>
For example:
```
3
```


<b>Request:</b><br>
GET<br>
`http://localhost:8080/places/{id}`<br>
Response: {id} place details<br>
For example:
```
{
  "id": 3,
  "name": "New York"
}
```


<b>Request:</b><br>
POST<br>
`http://localhost:8080/places/{id}`<br>
Parameters:<br>
name - new place name<br>
<br>
Renames {id} place<br>


<b>Request:</b><br>
DELETE<br>
`http://localhost:8080/places/{id}`<br>
Removes {id} place<br>


<b>Request:</b><br>
POST<br>
`http://localhost:8080/transits`<br>
Header: Content-Type: application/json<br>
Content:<br>
```
{
  "id": null,
  "departureEvent": {
    "placeId": 2,
	"time": "2017-06-24T17:07+02:00"
  },
  "arrivalEvent": {
    "placeId": 3,
	"time": "2017-06-24T19:10+02:00"
  }
}
```
<br>
Creates new transit<br>
Response: Transit id<br>
For example:<br>
```
 5<br> 
```
<br>


<b>Request:</b><br>
GET<br>
`http://localhost:8080/transits/{id}`<br>
Response: {id} transit details<br>
For example:
```
{
    "id": 5,
    "departureEvent": {
        "placeId": 2,
        "time": "2017-06-24T17:07+02:00"
    },
    "arrivalEvent": {
        "placeId": 3,
        "time": "2017-06-24T19:10+02:00"
    }
}
```


<b>Request:</b><br>
DELETE<br>
`http://localhost:8080/transits/{id}`<br>
Removes {id} transit<br>
