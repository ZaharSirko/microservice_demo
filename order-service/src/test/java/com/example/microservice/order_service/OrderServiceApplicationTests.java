package com.example.microservice.order_service;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.hamcrest.MatcherAssert.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

	@ServiceConnection
	static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:latest");
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;

	}

	static {
		postgresqlContainer.start();
	}

	@Test
	void souldSubmitOrder() {
		String submitOrderJSON = """
				{
					"skuCode": "phone_14",
				    "price": 100,
				    "quantity": 100
				}
				""";
		var resposeBodyStirng = RestAssured.given()
				.contentType("application/json")
				.body(submitOrderJSON)
				.when()
				.post("/orders/add")
				.then()
				.log().all()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("orderNumber", Matchers.notNullValue())
				.body("skuCode", Matchers.equalTo("phone_14"))
				.body("price", Matchers.equalTo(100))
				.body("quantity", Matchers.equalTo(100));

	}
}
