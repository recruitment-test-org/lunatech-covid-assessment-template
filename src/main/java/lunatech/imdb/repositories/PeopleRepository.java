package lunatech.imdb.repositories;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lunatech.imdb.domain.Person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Singleton
public class PeopleRepository {

  private DataSource pool;

  @Inject
  public PeopleRepository(DataSource pool) {
    this.pool = pool;
  }

  /**
   * Finds a `Person` from the Database by ID.
   *
   * @param id Person Id
   * @return Some `Person` if exists in the DB, Empty otherwise.
   */
  public Optional<Person> getPerson(String id) {
    try (Connection conn = pool.getConnection();
         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM name_basics WHERE nconst = ?")) {

      pstmt.setString(1, id);
      ResultSet rs = pstmt.executeQuery();

      Optional<Person> person;
      if (rs.next()) {
        person = Optional.of(personMapper(rs));
      } else {
        person = Optional.empty();
      }

      return person;
    } catch (Throwable t) {
      throw new RuntimeException("Failed to get a person by id " + id, t);
    }
  }

  /**
   * Finds the `Title`s a `Person` is known for
   *
   * @param id Person Id
   * @return List of known `Title`s, can be empty if there are not titles the person is known for
   */
  public String[] getTitlesKnownFor(String id) {
    try (Connection conn = pool.getConnection();
         PreparedStatement pstmt = conn.prepareStatement("SELECT knownfortitles FROM name_basics WHERE nconst = ?")) {

      pstmt.setString(1, id);
      ResultSet rs = pstmt.executeQuery();

      String[] titles;
      if (rs.next() &&
          rs.getString(1) != null) {
        titles = rs.getString(1).split(",");
      } else {
        titles = new String[]{};
      }

      return titles;
    } catch (Throwable t) {
      throw new RuntimeException("Failed to get a known titles for by id " + id, t);
    }
  }

  /**
   * Coverts a `Row` to a `Person` class
   */
  public static Person personMapper(ResultSet row) throws SQLException {
    return new Person(
        row.getString("nconst"),
        row.getString("primaryname"),
        fromYear(row, "birthyear"),
        fromYear(row, "deathyear"),
        fromCsv(row, "primaryprofession")
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
  private static List<String> fromCsv(ResultSet row, String column) throws SQLException {
    return Optional.ofNullable(row.getString(column)).map(p -> Arrays.asList(p.split(","))).orElse(List.of());
  }

}
