package cohort_65.java.personservice.person.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Employee extends Person{
    String company;
    Integer salary;


    public Employee(Integer id, String name,
                    LocalDate birthDate, Address address, String company, Integer salary) {
        super(id, name, birthDate, address);
        this.company = company;
        this.salary = salary;
    }
}
