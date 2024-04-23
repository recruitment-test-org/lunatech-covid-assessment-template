package lunatech.imdb.controllers.dtos;

import java.util.List;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Crew {
  private final List<Person> directors;
  private final List<Person> writers;
  
  public Crew(List<Person> directors, List<Person> writers) {
    this.directors = directors;
    this.writers = writers;
  }

  public static Crew fromDomain(
      List<lunatech.imdb.domain.Person> directors,
      List<lunatech.imdb.domain.Person> writers) {
    
    return new Crew(
      directors.stream().map(Person::fromDomain).toList(),
      writers.stream().map(Person::fromDomain).toList()
    );
  }

  public List<Person> getDirectors() {
    return directors;
  }

  public List<Person> getWriters() {
    return writers;
  }

}
