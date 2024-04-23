package lunatech.imdb.controllers.dtos;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Rating {
  private final Double averageRating;
  private final Integer numberOfVotes;
  
  public Rating(Double averageRating, Integer numberOfVotes) {
    this.averageRating = averageRating;
    this.numberOfVotes = numberOfVotes;
  }

  public static Rating fromDomain(lunatech.imdb.domain.Rating rating) {
    return new Rating(rating.getAverageRating(), rating.getNumberOfVotes());
  }

  public Double getAverageRating() {
    return averageRating;
  }

  public Integer getNumberOfVotes() {
    return numberOfVotes;
  }

}
