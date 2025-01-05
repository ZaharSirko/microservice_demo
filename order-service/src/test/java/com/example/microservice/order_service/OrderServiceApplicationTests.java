package com.example.microservice.order_service;

import com.example.microservice.order_service.client.InventoryClient;
import com.example.microservice.order_service.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.hamcrest.MatcherAssert.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
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

		InventoryClientStub.stubInventoryCall("phone_14",100);

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
