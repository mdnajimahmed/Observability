# Build and Run locally
- docker needs to be installed
- java 21 or above
- ./gradlew clean build
- java -DOTEL_RESOURCE_ATTRIBUTES="service.instance.id=local01,service.version=gitsha,environment=dev" -DOTEL_SERVICE_NAME="product-aggregator" -DJWT_ISSUER_URI="YOUR_JWT_ISSUER_URI" -jar build/libs/product-aggregator-0.0.1-SNAPSHOT.jar