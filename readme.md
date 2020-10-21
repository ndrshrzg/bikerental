# BikeRental API
This is a standalone application realising a backend for users to rent and return bicycles.


For capabilites of the application, please refer to the [API specification](api/bike_rental_apispec.yaml). You can use [Swagger Editor](https://editor.swagger.io/) to visualize it. 

#### Requirements
Following software is required to run.
- java: openjdk 11+28
- gradle: 6.5
- docker: 19.03.13
- docker-compose: 1.25.4
<p>
Tested on Fedora 32 (kernel 5.8.15).

## Build and Test
Build the project by running `gradle build`.


Run a set of unit tests with `gradle test`.

## Deployment
To build the application, application container image and run the application, simply run 

`gradle runBikeRental`

This will compile the Spring Boot application, run unit tests and build a docker container image containing the application. It will then run `docker-compose` to spin up an application container with a MySQL (8.0.21) database.

After both containers are started, give the application a few seconds to acquire a connection to the database.

### Known issues
Due to the way gradle locks files, the `runBikeRental` task fails when creating the container image (`buildBikeRentalImage` task). On Windows hosts, you need to run the following commands
1. `gradle build`
2. `docker -t bikerental-image:latest .`
3. `gradle composeUp`

## Usage

Refer to [API specification](api/bike_rental_apispec.yaml) for available endpoints, usage, and expected application responses. Once the application is running, you can send HTTP requests to the application via localhost at port 8080.

Note that to issue these requests, HTTP Basic authentication is required.

### Available sample users and passwords
The following Users are preloaded into the application on startup.

| Username | Password |
| --- | --- |
| Hans | temp1234! |
| Henry | 1234temp! |
| Thomas | !temp1234 |
 
### Sample calls to the endpoints
Get a list of all available bikes


``curl -v --user Hans:temp1234! localhost:8080/bikes``

Get detailed info on a specified bike


``curl -v --user Hans:temp1234! localhost:8080/bikes/1``


Rent a specified bike


``curl -v --user Hans:temp1234! -X POST localhost:8080/rent/1``


Return the rented bike


``curl -v --user Hans:temp1234! -X POST localhost:8080/return/1``

