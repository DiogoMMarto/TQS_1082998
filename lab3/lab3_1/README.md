# 3.1 Employee manager example

## a) Identify a couple of examples that use AssertJ expressive methods chaining.

On `A_EmployeeRepositoryTest` line 75.

```java
    assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
``` 

On `B_EmployeeService_UnitTest` line 112.
```java
    assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());
``` 

## b) Identify an example in which you mock the behavior of the repository (and avoid involving a database). 

On `B_EmployeeService_UnitTest`.

```java
    @Mock( lenient = true)
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;
```
Creates a mock `EmployeeRepository` and injects in the `EmployeeServiceImpl`, which means `employeeRepository` in this test doesnt make use of any database.

```java
    Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
    Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
    Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
    Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
    Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
    Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
```
In the `setUp()` function we have this code that mocks the behavior of a real repository.

## c) What is the difference between standard @Mock and @MockBean?

`@Mock` is part of mockito framework and allows use to create mocks that model behaviour of classes. It doesnt work with SpringBoot.

`@MockBean` is part of Springboot. It is used to reaplace a bean with a mock and allows to use the startegy of mocking inside of Spring ApplicationContext.

## d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?

`application-integrationtest.properties` is a configuration file used in integration tests. In our case it can allows to use a real database instead of h2.

On `D_EmployeeRestControllerIT` line 38. Uncommeting this line allows to use a real data base.

```java
// @TestPropertySource(locations = "application-integrationtest.properties")
```

## e) the sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences? 

- Test C uses `@WebMvcTest` which allows you to test boundary components (controllers) and doesnt load the full Spring boot application. We then can `@MockBean` to simulate the behaviour of the service layer. This runs the tests in a simplified and light environment, simulating the behavior of an application server.

- Test D uses `@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = EmployeeMngrApplication.class)` which allows you to test the boundary components (controllers) while loading the full Spring Boot application but not using an API client. Several components 
will participate in this test (the REST endpoint, the service implementation, the repository, and the database)

- Test E uses `@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)` which allows you to test boundary components (controllers) while oading the full application and using an REST API with explicit HTTP client such as Tomcat. Similar to D we test several components (the REST endpoint, the service implementation, the repository, and the database).

In terms of speed Test C is fater than Test D which is faster then Test E.