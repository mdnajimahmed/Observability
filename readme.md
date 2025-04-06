# Prerequisites:
- You need to have docker installed. 
# Setting up microservices
Run the following commands from the project root
    - docker-compose build --no-cache
    - docker compose up -d
    - make sure all services are running and there is no obvious error in the log.

That's it!!! Your demo is ready

# Note:
product-aggregator uses spring security oauth2 resource server to secure it's public endpoint /product/:id . The details can be read here. https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html. I have used AWS cognito. By default it is disabled, to enable it we need to make two changes - 
-  SPRING_PROFILES_ACTIVE: secure
- in the .env file, set JWT_ISSUER_URI

By doing this we will get additional information in the span e.g user.email, which can be used to troubleshoot request specific to user!
