# 3.1 Employee manager example

## a) Identify a couple of examples that use AssertJ expressive methods chaining.

On `A_EmployeeRepositoryTest`.

```java
assertThat(found).isEqualTo(alex);
``` on line 37.

```java
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
``` on line 75.

## b) Identify an example in which you mock the behavior of the repository (and avoid involving a database). 

## c) What is the difference between standard @Mock and @MockBean?

## d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?

## e) the sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences? 