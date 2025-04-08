# About:
This is repo backs up a linkedin writeup which can be found here - https://www.linkedin.com/pulse/building-observable-spring-microservices-tempo-loki-prometheus-ahmed-u1vhc/?trackingId=Er2Igvw4UGuZqyAlzT1wUA%3D%3D
# Running this repo:
- Install the latest version of the docker
- run command  `docker-compose build --no-cache`
- run command `docker compose up -d`

Make sure all services are running and there is no obvious error in the log of each respective services(Microservices , huh!!!). That's it, Your demo is ready!

# Note:
product-aggregator uses spring security oauth2 resource server to secure it's public endpoint /product/:id . The details can be read here. https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html. I have used AWS cognito. By default it is disabled, to enable it we need to make two changes - 
-  SPRING_PROFILES_ACTIVE: secure
- in the .env file, set JWT_ISSUER_URI

By doing this we will get additional information in the span e.g user.email, which can be used to troubleshoot request specific to user!

