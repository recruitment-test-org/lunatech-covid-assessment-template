package lunatech.imdb.repositories;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lunatech.imdb.domain.Cast;
import lunatech.imdb.domain.Person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class CastRepository {

  private DataSource pool;

  @Inject
  public CastRepository(DataSource pool) {
    this.pool = pool;
  }

  /**
   * Finds a `Title`'s Cast from the Database by ID.
   *
   * @param id Title Id
   * @return Some `Title`'s Cast if exists in the DB.
   */
  public List<Cast> getCast(String id) {
    String query = "SELECT nb.*, category, job, characters " +
        "FROM title_principals tp " +
        "INNER JOIN name_basics nb ON tp.nconst = nb.nconst " +
        "WHERE tconst = ? " +
        "ORDER BY ordering";

    try (Connection conn = pool.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

      pstmt.setString(1, id);
      ResultSet rs = pstmt.executeQuery();

      List<Cast> cast = new ArrayList<>();
      while (rs.next()) {
        cast.add(castMapper(rs));
      }

      return cast;
    } catch (Throwable t) {
      throw new RuntimeException("Failed to get cast by id " + id, t);
    }
  }

  /**
   * Finds a `Title`'s Directors from the Database by ID.
   *
   * @param id Title Id
   * @return Some `Title`'s directors if exists in the DB.
   */
  public List<Person> getDirectors(String id) {
    return getCrew(id, "directors");
  }

  /**
   * Finds a `Title`'s Writers from the Database by ID.
   *
   * @param id Title Id
   * @return Some `Title`'s writers if exists in the DB.
   */
  public List<Person> getWriters(String id) {
    return getCrew(id, "writers");
  }

  /**
   * Finds a `Title`'s Crew (either directors or writers) from the Database by ID.
   *
   * @param id Title Id
   * @return Some `Title`'s crew if exists in the DB.
   */
  private List<Person> getCrew(String id, String column) {
    try (Connection conn = pool.getConnection();
         PreparedStatement pstmt1 = conn.prepareStatement("SELECT " + column + " FROM title_crew WHERE tconst = ?");
         PreparedStatement pstmt2 = conn.prepareStatement("SELECT * FROM name_basics WHERE nconst = ANY(?)")) {

      pstmt1.setString(1, id);
      ResultSet crewList = pstmt1.executeQuery();

      List<Person> crew = new ArrayList<>();
      if (crewList.next()) {
        pstmt2.setObject(1, crewList.getString(column).split(","));
        ResultSet crewRows = pstmt2.executeQuery();

        while (crewRows.next()) {
          crew.add(PeopleRepository.personMapper(crewRows));
        }
      }

      return crew;
    } catch (Throwable t) {
      throw new RuntimeException("Failed to get crew by id " + id, t);
    }
  }

  /**
   * Coverts a `Row` to a `Cast` class
   */
  private Cast castMapper(ResultSet row) throws SQLException {
    Person person = PeopleRepository.personMapper(row);

    return new Cast(
        person,
        row.getString("category"),
        Optional.ofNullable(row.getString("job")),
        List.of()
    );
  }

  ;
}
