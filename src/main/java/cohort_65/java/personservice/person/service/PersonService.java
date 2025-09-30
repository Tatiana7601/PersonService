package cohort_65.java.personservice.person.service;

import cohort_65.java.personservice.person.dto.*;

public interface PersonService {
    boolean addPerson(PersonDto personDto);

    PersonDto findPersonById(Integer id);

    PersonDto removePersonById(Integer id);

    PersonDto updatePersonName(Integer id, String name);

    PersonDto updatePersonAddress(Integer id, AddressDto addressDto);

    Iterable <PersonDto> findPersonsByCity(String city);

    Iterable <PersonDto> findPersonsByName(String name);

    Iterable <PersonDto>findPersonsBetweenAge(Integer from, Integer to);

    Iterable<CityPopulationDto> getCityPopulation();

    Iterable<EmployeeDto> findEmployeeBySalary(Integer min, Integer max);

    Iterable<ChildDto> findAllChildren();
}