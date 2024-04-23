package lunatech.imdb.services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lunatech.imdb.domain.Person;
import lunatech.imdb.domain.Title;
import lunatech.imdb.repositories.PeopleRepository;
import lunatech.imdb.repositories.TitleRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Service responsible for `People` (or `Person`)
 */
@Singleton
public class PeopleService {

  @Inject
  public PeopleRepository repository;

  @Inject
  public TitleRepository titleRepository;

  public PeopleService(PeopleRepository repository, TitleRepository titleRepository) {
    this.repository = repository;
    this.titleRepository = titleRepository;
  }

  /**
   * Finds a `Person` from the Database by ID.
   *
   * @param id Person Id
   * @return Some `Person` if exists in the DB, Empty otherwise.
   */
  public Optional<Person> getPerson(String id) {
    return repository.getPerson(id);
  }

  /**
   * Finds the `Title`s a `Person` is known for
   *
   * @param id Person Id
   * @return List of known `Title`s, can be empty if there are not titles the person is known for
   */
  public List<Title> getKnownForTitles(String id) {
    String[] titleNames = repository.getTitlesKnownFor(id);

    return Arrays.stream(titleNames)
        .map(titleRepository::getTitle)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
  }

}
