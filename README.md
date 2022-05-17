# Distance Calculator

### Local setup & deployment

Under project root directory:

* Run `docker-compose up` to start docker container for postgres database
* Under `application.properties` configure `app.should-init-postcode-data` to enable/disable initial data import for postcode and coordinates
* Run `mvn spring-boot:run` to start the application

###Endpoints

Calculate distance
````
POST /api/calculate-distance
Host: localhost:8080
Authorization: USER
Content-Type: application/json
Content-Length: 63

Request:
{
  "postcodeFrom": "AB10 1XG",
  "postcodeTo": "AB21 0AL"
}

Response:
{
    "from": {
        "postcode": "AB10 1XG",
        "latitude": "57° 8' 38'' N",
        "longitude": "2° 6' 53'' W"
    },
    "to": {
        "postcode": "AB21 0AL",
        "latitude": "57° 15' 47'' N",
        "longitude": "2° 9' 32'' W"
    },
    "distance": 13.51,
    "unit": "km"
}
````

Update coordinates by postcode
````
PUT /api/postcode-coordinate/{postcode}
Host: localhost:8080
Authorization: ADMIN
Content-Type: application/json
Content-Length: 83

Request:
{
  "latitude": 11.123456,
  "longitude": 22.567890
}

Response:
Status 200 (OK)
````

Get Coordinates by postcode
````
GET /api/postcode-coordinate/{postcode}
Host: localhost:8080
Authorization: USER or ADMIN
Content-Type: application/json
Content-Length: 83

Response:
{
    "id": 998413,
    "postcode": "IV51 0AE",
    "latitude": 11.123456,
    "longitude": 22.567890
}
````

### Authenticated Users

| Name          | Username | Password |    Role |
|---------------|:--------:|---------:|--------:|
| Administrator |  admin   |    admin |   ADMIN |
| Regular user  |   user   |     user |    USER |
