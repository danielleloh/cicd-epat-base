package sg.edu.nus.iss.d13revision.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sg.edu.nus.iss.d13revision.models.Person;

class PersonServiceTest {

    private PersonService personService;

    @BeforeEach
    void setUp() {
        personService = new PersonService();
    }

    @Test
    void getPersons_returnsInitialSeedData() {
        List<Person> persons = personService.getPersons();

        assertEquals(2, persons.size());
        assertEquals("Mark", persons.get(0).getFirstName());
        assertEquals("Zuckerberg", persons.get(0).getLastName());
        assertEquals("Elon", persons.get(1).getFirstName());
        assertEquals("Musk", persons.get(1).getLastName());
    }

    @Test
    void addPerson_addsNewPersonToList() {
        Person person = new Person("Ada", "Lovelace");

        personService.addPerson(person);

        List<Person> persons = personService.getPersons();
        assertEquals(3, persons.size());
        assertTrue(persons.stream()
                .anyMatch(p -> "Ada".equals(p.getFirstName())
                        && "Lovelace".equals(p.getLastName())));
    }

    @Test
    void updatePerson_updatesMatchingPersonById() {
        Person original = personService.getPersons().get(0);
        Person updated = new Person(original.getId(), "Steve", "Jobs");

        personService.updatePerson(updated);

        List<Person> persons = personService.getPersons();
        Person found = persons.stream()
                .filter(p -> p.getId().equals(original.getId()))
                .findFirst()
                .orElse(null);

        assertNotNull(found);
        assertEquals("Steve", found.getFirstName());
        assertEquals("Jobs", found.getLastName());
        assertEquals(2, persons.size());
    }

    @Test
    void removePerson_removesMatchingPersonById() {
        Person original = personService.getPersons().get(1);

        personService.removePerson(original);

        List<Person> persons = personService.getPersons();
        assertEquals(1, persons.size());
        assertFalse(persons.stream()
                .anyMatch(p -> p.getId().equals(original.getId())));
    }
}
