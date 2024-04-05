package tqs.labs.testcontainers.data;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.labs.testcontainers.model.Employee;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    
    public Employee findByEmployeeId(Long employeeId);

    public List<Employee> findAll();

}





