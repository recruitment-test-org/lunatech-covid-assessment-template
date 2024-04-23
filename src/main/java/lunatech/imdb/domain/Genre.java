package lunatech.imdb.domain;

/** A `Title` can have one or more genres (eg. `Action`, `Horror`, etc.)
 */
public enum Genre {

  Action("Action"), Adult("Adult"), Adventure("Adventure"), Animation("Animation"),
  Biography("Biography"), Comedy("Comedy"), Crime("Crime"), Documentary("Documentary"),
  Drama("Drama"), Family("Family"), Fantasy("Fantasy"), FilmNoir("Film-Noir"),
  GameShow("Game-Show"), History("History"), Horror("Horror"), Music("Music"),
  Musical("Musical"), Mystery("Mystery"), News("News"), RealityTV("Reality-TV"),
  Romance("Romance"), SciFi("Sci-Fi"), Short("Short"), Sport("Sport"), TalkShow("Talk-Show"),
  Thriller("Thriller"), War("War"), Western("Western"),
  Unknown("Unknown");

  public final String value;

  Genre(String value) {
    this.value = value;
  }

  public static Genre fromValue(String value) {
    for (Genre genre : values()) {
      if (value.equals(genre.value)) {
        return genre;
      }
    }

    return Unknown;
  }
}
