# CRUD with Spring Boot

This is a Java project built with Spring Boot that provides order and product management functionalities. 
It includes several components such as controllers, services, repositories, and entity classes to handle orders, customers and products.

## Features

- CRUD for customers and products.
- ADD and DELETE customer orders.
- Search customers, products, and orders.
- Search order details.

## Business Logic:

1. Customer’s phone and email must be unique.
2. Product ID must be unique.
3. Order number must be unique.
4. An order can contain multiple products and must include at least one product.
5. Order’s total value is a sum of the order’s product/s prices.
6. A product cannot be deleted after it has been used in an order.
7. A customer cannot be deleted after it has been used in an order.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- JUnit
- Mockito

## Getting Started

To get started with the project, follow these steps:

1. **Prerequisites**: Make sure you have Java and Maven installed on your system.
2. **Clone the repository**: Use Git to clone this repository to your local machine. link https://github.com/danielrv01/SpringBootCRUD
3. **Build the project**: Open a terminal or command prompt and navigate to the project's root directory. Run the following command to build the project and resolve dependencies:

```shell
mvn clean install
```

4. **Run the application**: After the build is successful, you can run the application using the following command:

```shell
mvn spring-boot:run
```

5. **Access the API**: Once the application is running, you can access the API endpoints using a REST client or tools like Postman. The base URL for the API is `http://localhost:8080`.

## Running Unit Tests

This project includes unit tests for services and repositories. To run the unit tests, navigate to the project's root directory in a terminal or command prompt and execute the following command:

```shell
mvn test
```

The tests will be executed, and the test results will be displayed in the console.


## Contact

If you have any questions or suggestions, feel free to reach out to the project maintainers.

## Acknowledgments

- [Spring Boot](https://spring.io/projects/spring-boot)
- [JUnit](https://junit.org/)
- [Mockito](https://site.mockito.org/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [java](https://dev.java/)