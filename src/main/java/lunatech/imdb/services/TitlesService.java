package lunatech.imdb.services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lunatech.imdb.domain.Cast;
import lunatech.imdb.domain.Person;
import lunatech.imdb.domain.Rating;
import lunatech.imdb.domain.Title;
import lunatech.imdb.repositories.CastRepository;
import lunatech.imdb.repositories.TitleRepository;
import lunatech.imdb.utils.Tuple2;

/**
 * Service responsible for `Title`
 */
@Singleton
public class TitlesService {


  public TitleRepository repository;

  public CastRepository cRepository;

  @Inject
  public TitlesService(TitleRepository repository, CastRepository castRepository) {
    this.repository = repository;
    this.cRepository = castRepository;
  }

  /**
   * Finds the top rated `Title`s for a given genre
   *
   * @param genre to find top rated titles for
   * @return list of top rated titles
   */
  public List<Title> getTopRatedMovies(String genre) {
    return repository.getTopRated(genre);
  }

  /**
   * Finds a `Title` from the Database by ID.
   *
   * @param id Title Id
   * @return Some `Title` if exists in the DB, Empty otherwise.
   */
  public Optional<Title> getTitle(String id) {
    return repository.getTitle(id);
  }

  /**
   * Finds a `Title`'s Cast from the Database by ID.
   *
   * @param id Title Id
   * @return Some `Title`'s Cast if exists in the DB.
   */
  public List<Cast> getCast(String id) {
    return cRepository.getCast(id);
  }

  /**
   * Finds a `Title`'s Rating from the Database by ID.
   *
   * @param id Title Id
   * @return Some `Title`'s Rating if exists in the DB.
   */
  public Optional<Rating> getRating(String id) {
    return repository.getRating(id);
  }

  /**
   * Finds a `Title`'s Crew (Directors and Writers) from the Database by ID.
   *
   * @param id Title Id
   * @return Some `Title`'s Crew, as a tuple, if exists in the DB.
   */
  public Optional<Tuple2<List<Person>, List<Person>>> getCrew(String id) {
    Optional<Title> title = repository.getTitle(id);

    if (title.isPresent()) {
      List<Person> directors = cRepository.getDirectors(id);
      List<Person> writers = cRepository.getWriters(id);

      return Optional.of(Tuple2.of(directors, writers));
    } else {
      return Optional.of(Tuple2.of(List.of(), List.of()));
    }
  }

}
