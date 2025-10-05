package cohort_65.java.personservice.person.controller;

import cohort_65.java.personservice.person.dto.*;
import cohort_65.java.personservice.person.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {
    @Mock
    PersonService personService;
    @InjectMocks
    PersonController personController;


    @Test
    void addPerson() {
        PersonDto dto = createPerson();
        when(personService.addPerson(dto)).thenReturn(true);

        boolean result = personController.addPerson(dto);

        assertTrue(result);
        verify(personService).addPerson(dto);
    }

    @Test
    void addEmployee() {
        EmployeeDto dto = createEmployee();
        when(personService.addPerson(dto)).thenReturn(true);

        boolean result = personController.addPerson(dto);

        assertTrue(result, "The controller should return true when adding an Employee");
        verify(personService).addPerson(dto);
    }

    @Test
    void addChild() {
        ChildDto dto = createChild();
        when(personService.addPerson(dto)).thenReturn(true);

        boolean result = personController.addPerson(dto);

        assertTrue(result, "The controller should return true when adding an Child");
        verify(personService).addPerson(dto);
    }


    @Test
    void findPersonById() {
            PersonDto expected = createPerson();
            int id = 1;

            when(personService.findPersonById(id)).thenReturn(expected);

            PersonDto actual = personController.findPersonById(id);


            assertEquals(expected, actual);
            verify(personService).findPersonById(id);

    }

    @Test
    void removePersonById() {
        PersonDto expected = createPerson();
        int id = 2;

        when(personService.removePersonById(id)).thenReturn(expected);

        PersonDto actual = personController.removePersonById(id);

        assertEquals(expected, actual);
        verify(personService).removePersonById(id);

    }

    @Test
    void updatePersonName() {
        int id = 3;
        String newName = "Alice";

        PersonDto updated = createPerson();
        updated.setName(newName);

        when(personService.updatePersonName(id, newName)).thenReturn(updated);

        PersonDto result = personController.updatePersonName(id, newName);

        assertEquals(newName, result.getName());
        verify(personService).updatePersonName(id, newName);

    }

    @Test
    void updatePersonAddress() {
        int id = 4;

        AddressDto newAddress = new AddressDto();
        newAddress.setCity("Berlin");
        newAddress.setStreet("Main Street");
        newAddress.setBuilding(10);

        PersonDto updated = createPerson();
        updated.setAddress(newAddress);

        when(personService.updatePersonAddress(id, newAddress)).thenReturn(updated);

        PersonDto result = personController.updatePersonAddress(id, newAddress);

        assertEquals("Berlin", result.getAddress().getCity());
        verify(personService).updatePersonAddress(id, newAddress);
    }

    @Test
    void findPersonsByCity() {
        String city = "Hamburg";
        List<PersonDto> list = List.of(createPerson());

        when(personService.findPersonsByCity(city)).thenReturn(list);

        Iterable<PersonDto> result = personController.findPersonsByCity(city);

        assertEquals(list, result);
        verify(personService).findPersonsByCity(city);
    }

    @Test
    void findPersonsByCity_EmptyList() {
        String city = "Nowhere";
        when(personService.findPersonsByCity(city)).thenReturn(List.of());

        Iterable<PersonDto> result = personController.findPersonsByCity(city);

        assertTrue(((List<?>) result).isEmpty(), "Result should be empty when no persons in city");
        verify(personService).findPersonsByCity(city);
    }


    @Test
    void findPersonsByName() {
        String name = "John";
        List<PersonDto> list = List.of(createPerson());

        when(personService.findPersonsByName(name)).thenReturn(list);

        Iterable<PersonDto> result = personController.findPersonsByName(name);

        assertEquals(list, result);
        verify(personService).findPersonsByName(name);
    }

    @Test
    void findPersonsByName_EmptyList() {
        String name = "Unknown";  // Ім'я, якого немає в базі
        when(personService.findPersonsByName(name)).thenReturn(List.of());

        Iterable<PersonDto> result = personController.findPersonsByName(name);

        assertTrue(((List<?>) result).isEmpty(), "Result should be empty when no persons with this name");
        verify(personService).findPersonsByName(name);
    }

    @Test
    void findPersonsBetweenAge() {
        int from = 20, to = 30;
        List<PersonDto> list = List.of(createPerson());

        when(personService.findPersonsBetweenAge(from, to)).thenReturn(list);

        Iterable<PersonDto> result = personController.findPersonsBetweenAge(from, to);

        assertEquals(list, result);
        verify(personService).findPersonsBetweenAge(from, to);
    }

    @Test
    void getCityPopulation() {
        List<CityPopulationDto> list = List.of(new CityPopulationDto("Berlin", 1000000L));

        when(personService.getCityPopulation()).thenReturn(list);

        Iterable<CityPopulationDto> result = personController.getCityPopulation();

        assertEquals(list, result);
        verify(personService).getCityPopulation();
    }

    @Test
    void findEmployeeBySalary() {
        int min = 2000, max = 4000;
        List<EmployeeDto> list = List.of(createEmployee());

        when(personService.findEmployeeBySalary(min, max)).thenReturn(list);

        Iterable<EmployeeDto> result = personController.findEmployeeBySalary(min, max);

        assertEquals(list, result);
        verify(personService).findEmployeeBySalary(min, max);
    }

    @Test
    void findAllChildren() {
        List<ChildDto> list = List.of(createChild());

        when(personService.findAllChildren()).thenReturn(list);

        Iterable<ChildDto> result = personController.findAllChildren();

        assertEquals(list, result);
        verify(personService).findAllChildren();
    }



    private PersonDto createPerson() {
        PersonDto p = new PersonDto();
        p.setId(1);
        p.setName("John");
        p.setBirthDate(LocalDate.of(1990, 1, 1));

        AddressDto address = new AddressDto();
        address.setCity("Berlin");
        address.setStreet("Hauptstrasse");
        address.setBuilding(10);

        p.setAddress(address);
        return p;
    }

    private EmployeeDto createEmployee() {
        EmployeeDto e = new EmployeeDto();
        e.setId(2);
        e.setName("Max");
        e.setBirthDate(LocalDate.of(1985, 5, 20));
        e.setCompany("Siemens");
        e.setSalary(3500);
        return e;
    }

    private ChildDto createChild() {
        ChildDto c = new ChildDto();
        c.setId(3);
        c.setName("Lena");
        c.setBirthDate(LocalDate.of(2018, 7, 12));
        c.setKindergarten("Happy Kids");
        return c;
    }
}