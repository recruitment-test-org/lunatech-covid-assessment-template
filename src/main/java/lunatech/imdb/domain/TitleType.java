package lunatech.imdb.domain;

/** A `Title` can have one type (eg. `Movie`, `TvSeries`, etc.)
 */
public enum TitleType {

  Movie("movie"), TvMovie("tvMovie"),
  TvSeries("tvSeries"), Video("video"),
  Unknown("unknown");

  public final String value;

  TitleType(String value) {
    this.value = value;
  }

  public static TitleType fromValue(String value) {
    for (TitleType tt : values()) {
      if (tt.value.equalsIgnoreCase(value)) {
        return tt;
      }
    }

    return Unknown;
  }
}
