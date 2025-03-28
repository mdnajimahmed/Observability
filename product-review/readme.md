# Build and Run locally
- docker needs to be installed
- if this command is not already run then
    - docker run --name my-postgres -e POSTGRES_USER=myuser -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 -v pgdata:/var/lib/postgresql/data -d postgres
- java 21 or above
- ./gradlew clean build
- java -DOTEL_RESOURCE_ATTRIBUTES="service.instance.id=local01,service.version=gitsha,environment=dev" -DOTEL_SERVICE_NAME="product-review"
