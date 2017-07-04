# profiles-browser
This is a simple RESTful web service.<br>
It accepts Facebook profiles and returns single profiles as well as collective data based on submited ones.<br>
<br>

## To run this application
Type the following commands:
```
cd profiles-browser
mvn install
```

## Using web API

<b>Request:</b><br>
PUT<br>
`http://localhost:8080/profiles`<br>
Example content:<br>
```
{
      "id" : "1",
      "birthday" : 401280850089,
      "firstname" : "Luna",
      "lastname" : "Kling",
      "occupation" : "Direct Applications Administrator",
      "gender" : "female",
      "city" : {
        "countryName" : "United Kingdom",
        "cityName" : "London",
        "stateName" : "England",
        "coords" : {
          "lon" : -0.12574,
          "lat" : 51.50853
        }
      },
      "work" : "Lebsack - Rippin",
      "friends" : [ "68", "38", "122", "115", "127", "128", "91", "82", "31", "53", "32", "65", "76", "87" ],
      "school" : "Walter, Cartwright and Jerde",
      "location" : "London",
      "relationship" : "Married",
      "posts" : [ {
        "id" : "1",
        "message" : "Beautiful picture."
      }, {
        "id" : "2",
        "message" : "Great sound.! I bought this on for $1299.00 the Sunday before black Friday."
      }, {
        "id" : "3",
        "message" : "We definitely go by the new TV's now and just shake our heads at our mistake."
      } ]
}
```
<br>
Creates new Facebook profile<br>
<br>
<br>


<b>Request:</b><br>
GET<br>
`http://localhost:8080/profiles/{id}`<br>
Response: {id} Facebook profile details as above<br>


<b>Request:</b><br>
GET<br>
`http://localhost:8080/words`<br>
Response: appearance count per word map for all profiles posts<br>
For example:
```
{
    "remote": 1,
    "resolution": 1,
    "you": 11,
    "lighting": 2,
    "at": 2,
}
```


<b>Request:</b><br>
GET<br>
`http://localhost:8080/posts/{word}`<br>
Response: set of post id's containing word {word}<br>
For example:
```
[
    "2",
    "3"
]
```


<b>Request:</b><br>
GET<br>
`http://localhost:8080/profiles`<br>
Response: set of Facebook profiles sorted by firstname and lastname<br>
