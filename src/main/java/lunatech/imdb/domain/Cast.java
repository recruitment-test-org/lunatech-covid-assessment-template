package lunatech.imdb.domain;

import java.util.List;
import java.util.Optional;

/** A `Cast` is a person who worked in a `Title`
 */
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
