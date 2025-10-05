package cohort_65.java.personservice.person.service;

import cohort_65.java.personservice.person.dto.ChildDto;
import cohort_65.java.personservice.person.dto.EmployeeDto;
import cohort_65.java.personservice.person.dto.PersonDto;
import cohort_65.java.personservice.person.model.Child;
import cohort_65.java.personservice.person.model.Employee;
import cohort_65.java.personservice.person.model.Person;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PersonModelDtoMapper {

    private final ModelMapper modelMapper;

    public PersonDto mapPersonToDto(Person person) {
        //todo переробити через рефлексію
        if(person instanceof Child){
            return modelMapper.map( person, ChildDto.class);
        } else if(person instanceof Employee) {
            return modelMapper.map(person, EmployeeDto.class);
        }else {
            return modelMapper.map(person, PersonDto.class);
        }
    }

    public Person mapDtoToPerson(PersonDto personDto) {
        if(personDto instanceof ChildDto){
            return modelMapper.map( personDto, Child.class);
        } else if(personDto instanceof EmployeeDto) {
            return modelMapper.map(personDto, Employee.class);
        }else {
            return modelMapper.map(personDto, Person.class);
        }
    }

    public List<PersonDto> mapPersonListToDtoList(List<? extends Person> persons) {
        return persons.stream()
                .map(this::mapPersonToDto)
                .collect(Collectors.toList());
    }

}
