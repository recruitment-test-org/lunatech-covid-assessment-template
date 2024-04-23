package lunatech.imdb.domain;

/** A `Rating` represents how is a title rated (is it good? is it terrible?)
 */
public class Rating {

  private final Double averageRating;
  private final Integer numberOfVotes;
  
  public Rating(Double averageRating, Integer numberOfVotes) {
    this.averageRating = averageRating;
    this.numberOfVotes = numberOfVotes;
  }

  public Double getAverageRating() {
    return averageRating;
  }

  public Integer getNumberOfVotes() {
    return numberOfVotes;
  }

}
