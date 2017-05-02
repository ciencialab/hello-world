# meower
This is a simple Tweeter like social networking web application.

Its functionality is accessible both by web page as well as underlying RESTful web service.
#

**To run this application**

Install WildFly 10 or above:

Go to the WildFly installation directory.

Run Wildfly server as follows:


On Linux:

`./bin/standalone.sh`


On Windows:

`.\\bin\\standalone.bat`


Then type the following commands:
```
cd meower
mvn install
```


**Using web page**

In your browser go to url:
`http://localhost:8080/meower/`


To add first post fill in the "User name" and "Post content" fields in the "My posts" section then press "Create post" button.

To add subsequent posts you don't need to fill in the "User name" field.


To change user context simply choose the user name from dropdown list then press "Change user" button.

You can open the above link in multiple browser windows/tabs to manage multiple users conveniently.


To start following other user fill in the "User name" field in the "Other users" section then press "Follow" button.

To stop following other user choose him/her from subsequent list then press "Unfollow" button.


**Using web API**

All the requests should be sent with the following headers:

- Content-Type: application/json

- Accept: application/json


Request:

GET

`http://localhost:8080/meower/webapi/users`

Response: All usernames list

For example:

[

  "A",

  "B"

]


Request:

POST

`http://localhost:8080/meower/webapi/user/{username}/post`

Body: {username}'s post content string

Creates {username} when needed and registers the post

Example response when post exceedes 140 characters:

`[PARAMETER]

[createPost.arg1]

[Post length cannot contain more than 140 characters]

[123456789 123456789 123456789 123456789 123456789 123456789 123456789 

123456789 123456789 123456789 123456789 123456789 123456789 123456789]`


Request:

GET

`http://localhost:8080/meower/webapi/user/{username}/posts`

Response: List of {username}'s posts in reverse chronological order

For example:

`[

  {

    "authorName": "A",
    
	"dateTimeCreated": "2017-05-01T23:22:57.070+02:00[Europe/Belgrade]",
    
	"content": "Hello post!"
  
  },
  
  {
    
	"authorName": "A",
    
	"dateTimeCreated": "2017-05-01T22:36:09.264+02:00[Europe/Belgrade]",
    
	"content": "Abrakadabra"
  
  },
  
  {
    
	"authorName": "A",
    
	"dateTimeCreated": "2017-05-01T22:17:17.188+02:00[Europe/Belgrade]",
    
	"content": "1st post"
  
  }

]`


Request:

GET

`http://localhost:8080/meower/webapi/user/{username}/followed-posts`

Response: List of all posts followed by {username} in reverse chronological order

Example for username C:

`[
  
  {
    
	"authorName": "A",
    
	"dateTimeCreated": "2017-05-01T23:22:57.070+02:00[Europe/Belgrade]",
    
	"content": "Hello post!"
  
  },
  
  {
    
	"authorName": "B",
    
	"dateTimeCreated": "2017-05-01T22:39:27.886+02:00[Europe/Belgrade]",
    
	"content": "bbb"
  
  },
  
  {
    
	"authorName": "A",
    
	"dateTimeCreated": "2017-05-01T22:36:09.264+02:00[Europe/Belgrade]",
    
	"content": "Abrakadabra"
  
  },
  
  {
    
	"authorName": "A",
    
	"dateTimeCreated": "2017-05-01T22:17:17.188+02:00[Europe/Belgrade]",
    
	"content": "1st post"
  
  }

]`


Request:

POST

`http://localhost:8080/meower/webapi/user/{username}/follow`

Body: username to start to follow by {username}


Request:

GET

`http://localhost:8080/meower/webapi/user/{username}/followed-users`

Response: List of usernames followed by {username}

Example for username C:

`[
  
  "A",
  
  "B"

]`


Request:

POST

`http://localhost:8080/meower/webapi/user/{username}/unfollow`

Body: username to stop to follow by {username}
