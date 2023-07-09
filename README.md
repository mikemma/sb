This is a demo project which demonstrate the use of several functionalities found in Spring Boot projects: 
1. REST API - the contract of the backend exposed to the frontend
2. Open Api documentation (formerly called Swagger UI) - a nice GUI used to inspect and test the REST API exposed by the backend
  The access links are: http://localhost:8080/api-docs (doesn't require auth) and http://localhost:8080/swagger-ui/index.html (requires auth)
3. H2 database - the simplest way to integrate a database into the project. The /h2-console also provides a nice gui to interract with the H2-DB. 
  Link access to the database: http://localhost:8080/h2-console (requires auth)
4. Spring security - implemented a simple way to secure the api under autentication. This expose 2 REST endpoints:
    - login: (user/pwd): http://localhost:8080/login
    - logout: http://localhost:8080/logout
5. Logging support - used the one which was already provided by SpringBoot: Logback

I started by creating a new Java Project, Spring Boot (Provided by Spring Initializr), with Maven, Spring 3.1.1,  Java17 and several dependencies.
* The OpenApi section is customized by OpenApiConfig class where title, description and version can be specified.
* The Spring Security section is customized by the WebSecurityConfig class in which plain password is specified (in production the BCryptPasswordEncoder should be used). 
* The REST API provided by the project is formed by 4 packages: controller, entity, repository and service.
    - controller.ProductsController represent the interface with the frontend (and can be tested via the Swagger UI GUI). For the demo, I added only one controller.
    - service.ProductService represent the api called by the controller in the backend. This API provides logic to create, retrieve, update and delete entities from the database. The controller only delegates the call to service and wrapps the response into a ReponseEntity.
    - repository.ProductRepository - this is used by the ProductService to access the entities from the database. The parent class CrudRepository already implements most of the funcitonality used by the service. I only added the 'findByName' feature.
    - entity.Product - this is a JPA entity class or a POJO which represents the 'products' table inside the database. This class represents the link with the H2-DB. I created a constructor with args in order to be able to make some unit tests and then I was forced to create also the non-args constructor.
    - unit tests are defined in ProductCOntrollerTest and ProductServiceTest classes
    - Logback is customized via the application.properties file
