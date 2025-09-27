package com.microservices.order_service;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");

	@LocalServerPort
	private Integer port;

	static {
		mySQLContainer.start();
	}

	@BeforeEach
	public void setup(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	void shouldSubmitOrder() {
		String orderRequest = """
				{
				    "orderNumber": "12345",
				    "skuCode": "ABC123",
				    "price": 100.00,
				    "quantity": 2
				}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(orderRequest)
				.when()
				.post("/api/v1/orders")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("orderNumber", Matchers.equalTo("12345"))
				.body("skuCode", Matchers.equalTo("ABC123"))
				.body("price", Matchers.equalTo(100.00f))
				.body("quantity", Matchers.equalTo(2));
	}

}
