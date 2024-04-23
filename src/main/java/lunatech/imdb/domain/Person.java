package lunatech.imdb.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/** Represents a person who participated in a film, or tv series. This
 * includes actors, actresses, producers, etc.
 */
public class Person {

  private final String id;
  private final String name;
  private final Optional<LocalDate> birthYear;
  private final Optional<LocalDate> deathYear;
  private final List<String> profession;
  
  public Person(String id, String name) {
    this(id, name, Optional.empty(), Optional.empty(), List.of());
  }

  public Person(String id, String name, Optional<LocalDate> birthYear, Optional<LocalDate> deathYear, List<String> profession) {
    this.id = id;
    this.name = name;
    this.birthYear = birthYear;
    this.deathYear = deathYear;
    this.profession = profession;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Optional<LocalDate> getBirthYear() {
    return birthYear;
  }

  public Optional<LocalDate> getDeathYear() {
    return deathYear;
  }

  public List<String> getProfession() {
    return profession;
  }

}
