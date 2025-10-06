package cohort_65.java.personservice.repository;

import cohort_65.java.personservice.person.dao.PersonRepository;
import cohort_65.java.personservice.person.dto.CityPopulationDto;
import cohort_65.java.personservice.person.model.Address;
import cohort_65.java.personservice.person.model.Child;
import cohort_65.java.personservice.person.model.Employee;
import cohort_65.java.personservice.person.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@Testcontainers
@DataJpaTest
class PersonRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);

        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create"); // створює таблиці заново
        registry.add("spring.jpa.show-sql", () -> "true"); // для дебагу SQL
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");
    }

    @Autowired
    PersonRepository personRepository;

    Address address1 = new Address("Berlin", "MainStr", 1);
    Address address2 = new Address("Cologne", "Rheinweg", 10);

    @BeforeEach
    void setUp() {
        personRepository.deleteAll(); // Очищаємо базу перед кожним тестом

// Створюємо простих людей
        Person p1 = new Person();
        p1.setName("Alice");
        p1.setBirthDate(LocalDate.of(2000, 1, 1));
        p1.setAddress(address1);

        Person p2 = new Person();
        p2.setName("Bob");
        p2.setBirthDate(LocalDate.of(1995, 5, 20));
        p2.setAddress(address2);

        // Створюємо працівників
        Employee e1 = new Employee();
        e1.setName("Charlie");
        e1.setBirthDate(LocalDate.of(1990, 2, 15));
        e1.setAddress(address1);
        e1.setCompany("CompanyA");
        e1.setSalary(5000);

        Employee e2 = new Employee();
        e2.setName("Dana");
        e2.setBirthDate(LocalDate.of(1985, 3, 10));
        e2.setAddress(address2);
        e2.setCompany("CompanyB");
        e2.setSalary(8000);

        // Створюємо дитину
        Child c1 = new Child();
        c1.setName("Eve");
        c1.setBirthDate(LocalDate.of(2018, 6, 5));
        c1.setAddress(address1);
        c1.setKindergarten("Kindergarten1");

        // Зберігаємо по одному — Hibernate сам генерує id
        personRepository.save(p1);
        personRepository.save(p2);
        personRepository.save(e1);
        personRepository.save(e2);
        personRepository.save(c1);

        // Додатково можна перевірити вставлені дані
        System.out.println("All persons: " + personRepository.findAll());
    }

    @Test
    void findByAddressCityIgnoreCase() {
        List<Person> all = personRepository.findAll();
        long countInBerlin = all.stream()
                .filter(p -> p.getAddress().getCity().equalsIgnoreCase("Berlin"))
                .count();
        assertEquals(3, countInBerlin);
    }

    @Test
    void findByNameIgnoreCase() {
        List<Person> alices = personRepository.findByNameIgnoreCase("alice");
        assertEquals(1, alices.size());
        assertEquals("Alice", alices.get(0).getName());
    }

    @Test
    void findByBirthDateBetween() {
        LocalDate start = LocalDate.of(1989, 1, 1);
        LocalDate end = LocalDate.of(1996, 12, 31);
        List<Person> results = personRepository.findByBirthDateBetween(start, end);
        assertEquals(2, results.size());
    }

    @Test
    void getCityPopulation() {
        List<CityPopulationDto> cityPop = personRepository.getCityPopulation();
        assertEquals(2, cityPop.size()); // Berlin і Cologne

        CityPopulationDto berlin = cityPop.stream()
                .filter(c -> c.getCity().equals("Berlin"))
                .findFirst()
                .orElseThrow();
        assertEquals(3, berlin.getPopulation());

        CityPopulationDto cologne = cityPop.stream()
                .filter(c -> c.getCity().equals("Cologne"))
                .findFirst()
                .orElseThrow();
        assertEquals(2, cologne.getPopulation());
    }

    @Test
    void findEmployeesBySalaryBetween() {
        List<Employee> employees = personRepository.findEmployeesBySalaryBetween(4000, 6000);
        assertEquals(1, employees.size());
        assertEquals("Charlie", employees.get(0).getName());
    }

    @Test
    void findAllChildrenQuery() {
        List<Child> children = personRepository.findAllChildrenQuery();
        assertEquals(1, children.size());
        assertEquals("Eve", children.get(0).getName());
    }
}