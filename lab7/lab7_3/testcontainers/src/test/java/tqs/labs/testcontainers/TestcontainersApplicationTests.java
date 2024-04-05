package tqs.labs.testcontainers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import tqs.labs.testcontainers.data.EmployeeRepository;
import tqs.labs.testcontainers.model.Employee;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestcontainersApplicationTests {

	@Container
	public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:12")
		.withUsername("diogo")
		.withPassword("password")
		.withDatabaseName("test");

	@Autowired
	private EmployeeRepository empRepository;

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.password", container::getPassword);
		registry.add("spring.datasource.username", container::getUsername);
	}
	
    @Test
    @Order(1)
    public void testCreateUser() {
        Employee employee = new Employee();
		employee.setName("Diogo");
		empRepository.save(employee);
    }

    @Test
    @Order(2)
    public void getUserDetails() {
		assertEquals(empRepository.findByEmployeeId(2l).getName(),"Jane Smith");
    }

	@Test
    @Order(3)
    public void getUserNumberUsers() {
		assertTrue( empRepository.count() == 3 );
    }

}