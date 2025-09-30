package cohort_65.java.personservice.person.controller;

import cohort_65.java.personservice.person.dto.*;
import cohort_65.java.personservice.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    final PersonService personService;

    @PostMapping
    public boolean addPerson(@RequestBody PersonDto personDto) {
        return personService.addPerson(personDto);
    }

    @GetMapping("/{id}")
    public PersonDto findPersonById(@PathVariable Integer id) {
        return personService.findPersonById(id);
    }

    @DeleteMapping("/{id}")
    public PersonDto removePersonById(@PathVariable Integer id) {
        return personService.removePersonById(id);
    }

    @PutMapping("/{id}/name/{name}")
    public PersonDto updatePersonName(@PathVariable Integer id, @PathVariable String name) {
        return personService.updatePersonName(id,name);
    }

    @PutMapping("/{id}/address")
    public PersonDto updatePersonAddress(@PathVariable Integer id, @RequestBody AddressDto addressDto) {
        return personService.updatePersonAddress(id,addressDto);
    }

    @GetMapping("/city/{city}")
    public Iterable<PersonDto> findPersonsByCity(@PathVariable String city) {
        return personService.findPersonsByCity(city);
    }

    @GetMapping("/name/{name}")
    public Iterable<PersonDto> findPersonsByName(@PathVariable String name) {
        return personService.findPersonsByName(name);
    }

    @GetMapping("/ages/{from}/{to}")
    public Iterable<PersonDto> findPersonsBetweenAge(@PathVariable Integer from, @PathVariable Integer to) {
        return personService.findPersonsBetweenAge(from,to);
    }

    @GetMapping("/population/city")
    public Iterable<CityPopulationDto> getCityPopulation() {
        return personService.getCityPopulation();
    }

    @GetMapping("/salary/{min}/{max}")
    public Iterable<EmployeeDto> findEmployeeBySalary(@PathVariable Integer min, @PathVariable Integer max) {
        return personService.findEmployeeBySalary(min, max);
    }

    @GetMapping("/children")
    public Iterable<ChildDto> findAllChildren() {
        return personService.findAllChildren();
    }


}