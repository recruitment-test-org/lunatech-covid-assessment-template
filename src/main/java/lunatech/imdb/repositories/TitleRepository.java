package lunatech.imdb.repositories;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lunatech.imdb.domain.Rating;
import lunatech.imdb.domain.Title;
import lunatech.imdb.domain.TitleType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Singleton
public class TitleRepository {

  private DataSource pool;

  @Inject
  public TitleRepository(DataSource pool) {
    this.pool = pool;
  }

  /**
   * Finds a `Title` from the Database by ID.
   *
   * @param id Title Id
   * @return Some `Title` if exists in the DB, Empty otherwise.
   */
  public Optional<Title> getTitle(String id) {
    try (Connection conn = pool.getConnection();
         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM title_basics WHERE tconst = ?")) {

      pstmt.setString(1, id);
      ResultSet rs = pstmt.executeQuery();

      Optional<Title> title;
      if (rs.next()) {
        title = Optional.of(titleMapper(rs));
      } else {
        title = Optional.empty();
      }

      return title;
    } catch (Throwable t) {
      throw new RuntimeException("Failed to get a title by id " + id, t);
    }
  }

  /**
   * Finds a `Title`'s Rating from the Database by ID.
   *
   * @param id Title Id
   * @return Some `Title`'s Rating if exists in the DB.
   */
  public Optional<Rating> getRating(String id) {
    try (Connection conn = pool.getConnection();
         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM title_ratings WHERE tconst = ?")) {

      pstmt.setString(1, id);
      ResultSet rs = pstmt.executeQuery();

      Optional<Rating> title;
      if (rs.next()) {
        title = Optional.of(ratingMapper(rs));
      } else {
        title = Optional.empty();
      }

      return title;
    } catch (Throwable t) {
      throw new RuntimeException("Failed to get a ratings by id " + id, t);
    }
  }

  /**
   * Fetches the top 10 top rated `Title`s by genre
   *
   * @param genre to find top rated titles for
   * @return list of top rated titles
   */
  public List<Title> getTopRated(String genre) {
    String query = "SELECT tb.* " +
        "FROM title_basics tb " +
        "INNER JOIN title_ratings tr ON tb.tconst = tr.tconst " +
        "WHERE tb.genres LIKE ? " +
        "ORDER BY tr.averagerating * tr.numvotes DESC " +
        "LIMIT 10";

    try (Connection conn = pool.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

      pstmt.setString(1, "%" + genre + "%");
      ResultSet rs = pstmt.executeQuery();

      List<Title> titles = new ArrayList<>();
      while (rs.next()) {
        titles.add(titleMapper(rs));
      }

      return titles;
    } catch (Throwable t) {
      throw new RuntimeException("Failed to get top rated titles for " + genre, t);
    }
  }

  /**
   * Coverts a `Row` to a `Title` class
   */
  private Title titleMapper(ResultSet row) throws SQLException {
    return new Title(
        row.getString("tconst"),
        TitleType.fromValue(row.getString("titletype")),
        row.getString("primarytitle"),
        row.getString("originaltitle"),
        fromYear(row, "startyear"),
        fromYear(row, "endyear"),
        Optional.of(row.getInt("runtimeminutes")).filter(m -> m > 0),
        fromCsv(row, "genres")
    );
  }

  /**
   * Coverts a `Row` to a `Rating` class
   */
  private Rating ratingMapper(ResultSet row) throws SQLException {
    return new Rating(
        row.getDouble("averagerating"),
        row.getInt("numvotes")
    );
  }

  /**
   * Converts a Year column into a `LocalDate`.
   * <p>
   * The date will be the 1st of January of that year.
   *
   * @param row    row to get value from
   * @param column year column name
   * @return some `LocalDate` if column isn't null, empty otherwise
   */
  private static Optional<LocalDate> fromYear(ResultSet row, String column) throws SQLException {
    int year = row.getInt(column);
    return year != 0 ? Optional.of(LocalDate.of(year, 1, 1)) : Optional.empty();
  }

  /**
   * Converts a CSV column to a List of values
   *
   * @param row    row to get CSV column from
   * @param column CSV column name
   * @return list of values. If column is empty or null, will return an empty list
   */
  private List<String> fromCsv(ResultSet row, String column) throws SQLException {
    return Optional.ofNullable(row.getString(column)).map(p -> Arrays.asList(p.split(","))).orElse(List.of());
  }

}
