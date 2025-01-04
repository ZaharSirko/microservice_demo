package com.example.microservice.product_service;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		postgreSQLContainer.start();
	}

	@Test
	void shouldCreateProduct() {
     String RequestBody = """
			 {
			 	"name": "Phone",
			     "description":"Simple phone",
			     "price": 1000
			 }
			 """;
      RestAssured.given()
			  .contentType("application/json")
			  .body(RequestBody)
			  .when()
			  .post("/products/add")
			  .then()
			  .statusCode(201)
			  .body("id", Matchers.notNullValue())
			  .body("name", Matchers.equalTo("Phone"))
			  .body("description", Matchers.equalTo("Simple phone"))
			  .body("price", Matchers.equalTo(1000));
	}

}
