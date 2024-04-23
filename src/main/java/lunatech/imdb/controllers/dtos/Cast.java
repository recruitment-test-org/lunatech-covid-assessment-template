package lunatech.imdb.controllers.dtos;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Cast {
  private final Person person;
  private final String category;
  private final Optional<String> job;
  private final List<String> characters;
  
  public Cast(Person person, String category, Optional<String> job, List<String> characters) {
    this.person = person;
    this.category = category;
    this.job = job;
    this.characters = characters;
  }

  public static Cast fromDomain(lunatech.imdb.domain.Cast cast) {
    return new Cast(
      Person.fromDomain(cast.getPerson()),
      cast.getCategory(),
      cast.getJob(),
      cast.getCharacters()
    );
  }

  public static Comparator<Cast> orderById = new Comparator<Cast>() {

    @Override
    public int compare(Cast cast1, Cast cast2) {
      return cast1.person.getId().compareTo(cast2.person.getId());
    }
    
  };

  public Person getPerson() {
    return person;
  }

  public String getCategory() {
    return category;
  }

  public Optional<String> getJob() {
    return job;
  }

  public List<String> getCharacters() {
    return characters;
  }

  
}
