package lunatech.imdb.controllers.dtos;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Title {
  private final String id;
  private final String type;
  private final String name;
  private final String originalName;
  private final Optional<LocalDate> startYear;
  private final Optional<LocalDate> endYear;
  private final Optional<Integer> runtimeInMinutes;
  private final List<String> genres;
  
  public Title(String id, String name, String originalName) {
    this(id, "", name, originalName, Optional.empty(), Optional.empty(), Optional.empty(), List.of());
  }

  public Title(String id, String type, String name, String originalName,
      Optional<LocalDate> startYear, Optional<LocalDate> endYear,
      Optional<Integer> runtimeInMinutes, List<String> genres) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.originalName = originalName;
    this.startYear = startYear;
    this.endYear = endYear;
    this.runtimeInMinutes = runtimeInMinutes;
    this.genres = genres;
  }

  public static Title fromDomain(lunatech.imdb.domain.Title t) {
    return new Title(
      t.getId(),
      t.getType().value,
      t.getName(),
      t.getOriginalName(),
      t.getStartYear(),
      t.getEndYear(),
      t.getRuntimeInMinutes(),
      t.getGenres().stream().map(g -> g.value).toList()
    );
  }

  public static Comparator<Title> compareById = new Comparator<Title>() {

    @Override
    public int compare(Title title1, Title title2) {
      return title1.id.compareTo(title2.id);
    }
    
  };

  public String getId() {
    return id;
  }

  public String getType() {
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

  public List<String> getGenres() {
    return genres;
  }
  
  
}
