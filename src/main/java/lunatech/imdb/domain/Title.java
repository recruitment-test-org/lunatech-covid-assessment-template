package lunatech.imdb.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/** A `Title` represents a production, like a film or a tv series.
 */
public class Title {

  private final String id;
  private final TitleType type;
  private final String name;
  private final String originalName;
  private final Optional<LocalDate> startYear;
  private final Optional<LocalDate> endYear;
  private final Optional<Integer> runtimeInMinutes;
  private final List<Genre> genres;
  
  public Title(String id, String name, String originalName) {
    this(id, TitleType.Unknown, name, originalName, Optional.empty(), Optional.empty(), Optional.empty(), List.of());
  }

  public Title(String id, TitleType type, String name, String originalName,
      Optional<LocalDate> startYear, Optional<LocalDate> endYear,
      Optional<Integer> runtimeInMinutes, List<String> genres) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.originalName = originalName;
    this.startYear = startYear;
    this.endYear = endYear;
    this.runtimeInMinutes = runtimeInMinutes;
    this.genres = genres.stream().map(Genre::fromValue).toList();
  }

  public String getId() {
    return id;
  }

  public TitleType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getOriginalName() {
    return originalName;
  }

  public Optional<LocalDate> getStartYear() {
    return startYear;
  }

  public Optional<LocalDate> getEndYear() {
    return endYear;
  }

  public Optional<Integer> getRuntimeInMinutes() {
    return runtimeInMinutes;
  }

  public List<Genre> getGenres() {
    return genres;
  }

}
