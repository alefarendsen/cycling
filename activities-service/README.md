# Activities Service documentation
This is a Micronaut-based service that pulls activities from Strava,
stores them in a local database and then exposes a REST API to
manipulate and retrieve those very same activities for use in other
applications.

This application was built by me to explore and familiarize myself
with various techniques and tools, as I've been away from development
for quite some time. Techniques used in this service include:
- [Micronaut](https://micronaut.io) (for DI, REST controllers and database access)

---

## Usage
This service should be runnable as is, as it is a pure Micronaut service.

A simple `./mvnw mn:run` should suffice to run the service.

You can test to see if the service is up by visiting

- http://localhost:8080/activity to see all activities
- http://localhost:8080/activity/{id}/records to 
see all performance data records of any particular activity





