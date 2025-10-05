package cohort_65.java.personservice.person.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "employee", value = EmployeeDto.class),
        @JsonSubTypes.Type(name = "child", value = ChildDto.class),
        @JsonSubTypes.Type(name = "person", value = PersonDto.class)
})
public class PersonDto {
    Integer id;
    String name;
    LocalDate birthDate;
    AddressDto address;
}