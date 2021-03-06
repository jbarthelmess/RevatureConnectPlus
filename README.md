# RevatureConnectPlus


## User stories
|As a(n) | I want to | So that |
|--------|-----------|---------|
|Associate|Create an Account|Participate in the company announcements|
|Associate|Log in to my Account|View and interact with my colleagues on the site|
|Associate|View Posts|Know how my colleagues and peers are doing and what they are talking about|
|Associate|Make Posts|Communicate to my colleagues and peers how and what I'm doing as it pertains to the company|
|Associate|Delete my Posts|I can change mistakes I've made to my posts or remove sensitive info I've accidentally posted|
|Associate|Like Posts|I can wordlessly, conveniently, and quickly interact with my colleagues|
|Associate|Comment On Posts|I can more clearly and deliberately interact with my colleagues when I have something more in-depth to say about what they have posted|
|Associate|View Account Info|See if I want to edit my account settings|
|Associate|Search for Posts|Find posts I want to refer to where I only remember part of what was in the post|

## Endpoints
#### User Endpoints
`POST /user/registration` <-- create new user account

`POST /user/login` <-- login

`DELETE /user` <-- delete user account

`PATCH /user` <-- update user info
#### POST ENDPOINTS
`GET /post` <-- get visible posts

`POST /post` <-- create new post

`POST /post/{id}/like` <-- like/unlike a post {id}

`POST /post/{id}/comment` <-- comment on a post {id}

`GET /post/{id}/comment` <-- view comments on post {id}

`PUT /post/{id}` <-- update a post {id}

`PUT /post/{id}/comment/{commentId}` <-- update a comment {id}

`DELETE /post/{id}` <-- delete post {id}

`DELETE /post/{id}/comment/{commentId}` <-- delete a comment on post {id} with {comment_id}
