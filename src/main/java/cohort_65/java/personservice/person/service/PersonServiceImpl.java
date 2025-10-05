package cohort_65.java.personservice.person.service;

import cohort_65.java.personservice.person.dao.PersonRepository;
import cohort_65.java.personservice.person.dto.*;
import cohort_65.java.personservice.person.dto.exception.PersonNotFoundException;
import cohort_65.java.personservice.person.model.Address;
import cohort_65.java.personservice.person.model.Child;
import cohort_65.java.personservice.person.model.Employee;
import cohort_65.java.personservice.person.model.Person;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, CommandLineRunner {

    final PersonRepository personRepository;
    final PersonModelDtoMapper personMapper;
    private final ModelMapper modelMapper;


    @Override
    public boolean addPerson(PersonDto personDto) {
        if (personRepository.existsById(personDto.getId())) {
            return false;
        }
        personRepository.save(personMapper.mapDtoToPerson(personDto));
        return true;
    }

    @Override
    public PersonDto findPersonById(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        return  personMapper.mapPersonToDto(person);
    }

    @Override
    public PersonDto removePersonById(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        personRepository.delete(person);
        return  personMapper.mapPersonToDto(person);
    }

    @Override
    public PersonDto updatePersonName(Integer id, String name) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        person.setName(name);
        personRepository.save(person);
        return personMapper.mapPersonToDto(person);
    }

    @Override
    public PersonDto updatePersonAddress(Integer id, AddressDto addressDto) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        if (addressDto == null) {
            return null;
        }
        person.setAddress(modelMapper.map(addressDto, Address.class));
        personRepository.save(person);
        return modelMapper.map(person, PersonDto.class);
    }
    @Override
    public List<PersonDto> findPersonsByCity(String city) {
        return personMapper.mapPersonListToDtoList(personRepository.findByAddressCityIgnoreCase(city));

    }

    @Override
    public List<PersonDto>findPersonsByName(String name) {
        return personMapper.mapPersonListToDtoList(personRepository.findByNameIgnoreCase(name));
    }

    @Override
    public List<PersonDto> findPersonsBetweenAge(Integer minAge, Integer maxAge) {
        LocalDate fromDate = LocalDate.now().minusYears(maxAge);
        LocalDate toDate = LocalDate.now().minusYears(minAge);

        return personMapper.mapPersonListToDtoList(personRepository.findByBirthDateBetween(fromDate, toDate));
    }

    @Override
    public Iterable<CityPopulationDto> getCityPopulation() {
        return personRepository.getCityPopulation();
    }

    @Override
    public Iterable<EmployeeDto> findEmployeeBySalary(Integer min, Integer max) {
        return personRepository.findEmployeesBySalaryBetween(min, max)
                .stream()
                .map(emp -> personMapper.mapPersonToDto(emp))
                .map(EmployeeDto.class::cast)
                .toList();
    }

    @Override
    public Iterable<ChildDto> findAllChildren() {
        return personRepository.findAllChildrenQuery()
                .stream()
                .map(personMapper::mapPersonToDto)
                .map(ChildDto.class::cast)
                .toList();

    }


    @Override
    public void run(String... args) throws Exception {
        if (personRepository.count() == 0) {
            Person person = new Person(1000, "John",
                    LocalDate.now().minusYears(20), new Address("Berlin", "Kantstr", 20));
            Child child = new Child(2000,
                    "Peter",
                    LocalDate.now().minusYears(5),
                    new Address("Berlin", "KantStr", 33),
                    "Kindergarten");
            Employee employee = new Employee(3000, "Karl", LocalDate.now().minusYears(30),
                    new Address("Berlin", "KantStr", 63),
                    "Apple", 8000);
            personRepository.save(person);
            personRepository.save(child);
            personRepository.save(employee);
        }
    }

}

