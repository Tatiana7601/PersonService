package cohort_65.java.personservice.person.dao;

import cohort_65.java.personservice.person.dto.CityPopulationDto;
import cohort_65.java.personservice.person.model.Child;
import cohort_65.java.personservice.person.model.Employee;
import cohort_65.java.personservice.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findByAddressCityIgnoreCase(String city);

    List<Person> findByNameIgnoreCase(String name);

    List<Person> findByBirthDateBetween(LocalDate birthDateAfter, LocalDate birthDateBefore);

    @Query("SELECT new cohort_65.java.personservice.person.dto.CityPopulationDto(p.address.city, COUNT(p)) " +
            "FROM Person p GROUP BY p.address.city")
    List<CityPopulationDto> getCityPopulation();

    @Query("SELECT e FROM Employee e WHERE e.salary BETWEEN :min AND :max")
    List<Employee> findEmployeesBySalaryBetween(Integer min, Integer max);


    @Query("SELECT c FROM Child c")
    List<Child> findAllChildrenQuery();
}