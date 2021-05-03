# test
This is a simple test App for employee data

<h2>Requirements</h2>
For building and running the application you need:

[JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) 

[Maven 3](https://maven.apache.org/)

<h2>Running the application locally</h2>

There are multiple ways to run this application on your local machine.
One way is to execute the main method in the com.activedgetechnologies.test.TestApplication class from your IDE.

Another is to run the the jar file inccluded on a java 11 environment.

Navigate to the command line and run the command below

`java -jar test-0.0.1-SNAPSHOT.jar`



Once the application has started succesfully, you can navigate to the  documenatation using the url below

http://localhost:1234/api/swagger-ui.html

![image](https://user-images.githubusercontent.com/28824081/116867729-85879000-ac05-11eb-90f1-829b7c85ccc7.png)

You should see a scrren that looks like this.

You can authenticate your session by making an HTTP post call to the url below.
A default user has been created on application start up.

http://localhost:1234/api/v1/auth/login

````
  curl -X POST "http://localhost:1234/api/v1/auth/login" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"email\": \"admin@activedgetechnologies.com\", \"password\": \"1234_ADMIN_PASSWORD\"}"
````

Request and Response bodies are as follows.

Request
````
  {
    "email": "admin@activedgetechnologies.com",
    "password": "1234_ADMIN_PASSWORD"
  }
````
Response
````
  {
    "message": "Login Successful",
    "data": {
      "tokenInfo": {
        "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBhY3RpdmVkZ2V0ZWNobm9sb2dpZXMuY29tIiwiaWF0IjoxNjIwMDM4OTg5LCJleHAiOjE2MjAxMjUzODl9.6zlXW65vZ9TYmzH3KLvBqLWzJnRSb8YRlZ0EbYY1wXxs5gzQMsklDOHaKWRgWKdGZnZGRPnD1MF7UG0WBNYBlQ",
        "tokenType": "BEARER"
      },
      "user": {
        "id": 1,
        "firstName": "Admin",
        "lastName": "User",
        "email": "admin@activedgetechnologies.com"
      }
    },
    "responseCode": "SUCCESS",
    "errors": null,
    "logId": null
  }
````

The accessToken passed can be used to authorize you subsequent requests.

This is done by passing the accesstoken with the prefix "bearer " in the HTTP "Authorization" header.





