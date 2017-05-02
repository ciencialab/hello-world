# meower
This is a simple Tweeter like social networking web application.<br>
Its functionality is accessible both by web page as well as underlying RESTful web service.

#

**To run this application**

Install WildFly 10 or above:


Go to the WildFly installation directory.<br>
Run Wildfly server as follows:<br>

On Linux:<br>
`./bin/standalone.sh`

On Windows:<br>
`.\\bin\\standalone.bat`

Then type the following commands:
```
cd meower
mvn install
```

**Using web page**

In your browser go to url:

`http://localhost:8080/meower/`

To add first post fill in the "User name" and "Post content" fields in the "My posts" section then press "Create post" button.<br>
To add subsequent posts you don't need to fill in the "User name" field.

To change user context simply choose the user name from dropdown list then press "Change user" button.<br>
You can open the above link in multiple browser windows/tabs to manage multiple users conveniently.

To start following other user fill in the "User name" field in the "Other users" section then press "Follow" button.<br>
To stop following other user choose him/her from subsequent list then press "Unfollow" button.

**Using web API**

All the requests should be sent with the following headers:
- Content-Type: application/json
- Accept: application/json


<b>Request:</b><br>
GET<br>
`http://localhost:8080/meower/webapi/users`<br>
Response: All usernames list<br>
For example:
```
[
  "A",
  "B"
]
```


<b>Request:</b><br>
POST<br>
`http://localhost:8080/meower/webapi/user/{username}/post`<br>
Body: {username}'s post content string<br>
Creates {username} when needed and registers the post<br>
Example response when post exceedes 140 characters:<br>
```
[PARAMETER]
[createPost.arg1]
[Post length cannot contain more than 140 characters]
[123456789 123456789 123456789 123456789 123456789 123456789 123456789 
123456789 123456789 123456789 123456789 123456789 123456789 123456789]
```


<b>Request:</b><br>
GET<br>
`http://localhost:8080/meower/webapi/user/{username}/posts`<br>
Response: List of {username}'s posts in reverse chronological order<br>
For example:
```
[
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
]
```


<b>Request:</b><br>
GET<br>
`http://localhost:8080/meower/webapi/user/{username}/followed-posts`<br>
Response: List of all posts followed by {username} in reverse chronological order<br>
Example for username C:
```
[
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
]
```


<b>Request:</b><br>
POST<br>
`http://localhost:8080/meower/webapi/user/{username}/follow`<br>
Body: username to start to follow by {username}


<b>Request:</b><br>
GET<br>
`http://localhost:8080/meower/webapi/user/{username}/followed-users`<br>
Response: List of usernames followed by {username}<br>
Example for username C:
```
[
  "A",
  "B"
]
```


<b>Request:</b><br>
POST<br>
`http://localhost:8080/meower/webapi/user/{username}/unfollow`<br>
Body: username to stop to follow by {username}
