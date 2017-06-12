# journey-planner
This is a simple RESTful web service.<br>
Currently there are implemented CRUD operations for journey-plan lifecycle only.<br>

## To run this application
Type the following commands:
```
cd journey-planner
mvn install
```

## Using web API

<b>Request:</b><br>
POST<br>
`http://localhost:8080/journey-plan`<br>
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
`http://localhost:8080/journey-plan/{id}`<br>
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
`http://localhost:8080/journey-plan/{id}`<br>
Parameters:<br>
name - new journey plan name<br>
<br>
Renames {id} journey plan<br>


<b>Request:</b><br>
DELETE<br>
`http://localhost:8080/journey-plan/{id}`<br>
Removes {id} journey plan<br>

<b>Request:</b><br>
POST<br>
`http://localhost:8080/place`<br>
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
`http://localhost:8080/place/{id}`<br>
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
`http://localhost:8080/place/{id}`<br>
Parameters:<br>
name - new place name<br>
<br>
Renames {id} place<br>


<b>Request:</b><br>
DELETE<br>
`http://localhost:8080/place/{id}`<br>
Removes {id} place<br>
