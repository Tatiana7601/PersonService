package cohort_65.java.personservice.person.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto extends PersonDto{
    String company;
    Integer salary;

}
