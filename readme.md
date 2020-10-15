# BikeRental API
This is a standalone application realising a backend for users to rent bicycles close to their proximity. <br>
You can find the API specification at api/bike_rental_apispec.yaml.
## Requirements
Have gradle, docker, docker-compose installed.
## Build
To build create a container serving the application by running: TBD 
## Deployment
Run the docker-compose.yml file with docker-compose up . This creates two containers, one holding a MySQL database and one holding the application.
## Test
curl -v localhost:8080/bikes (expect 401) <br>
curl -v --user yyyyy:xxxxxxx localhost:8080/bikes (expect 200) <br>
TBD