package lunatech.imdb.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Inject;
import lunatech.imdb.controllers.dtos.Cast;
import lunatech.imdb.controllers.dtos.Crew;
import lunatech.imdb.controllers.dtos.Rating;
import lunatech.imdb.controllers.dtos.Title;
import lunatech.imdb.services.TitlesService;

import java.util.List;
import java.util.Optional;

@Controller("/titles")
public class TitlesController {

  @Inject
  public TitlesService service;

  @Get(value = "/top-rated", produces = MediaType.APPLICATION_JSON)
  public HttpResponse<List<Title>> getTopRatedMovies(String genre) {
    return HttpResponse.ok(
        service.getTopRatedMovies(genre)
            .stream()
            .map(Title::fromDomain)
            .toList()
    );
  }

  @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
  public HttpResponse<Optional<Title>> getTitle(@PathVariable("id") String id) {
    return HttpResponse.ok(
        service.getTitle(id)
            .map(Title::fromDomain)
    );
  }

  @Get(value = "/{id}/cast", produces = MediaType.APPLICATION_JSON)
  public HttpResponse<List<Cast>> getCast(@PathVariable("id") String id) {
    return HttpResponse.ok(
        service.getCast(id)
            .stream()
            .map(Cast::fromDomain)
            .sorted(Cast.orderById)
            .toList()
    );
  }

  @Get(value = "/{id}/crew", produces = MediaType.APPLICATION_JSON)
  public HttpResponse<Optional<Crew>> getCrew(@PathVariable("id") String id) {
    return HttpResponse.ok(
        service.getCrew(id).map(tuple -> Crew.fromDomain(tuple._1, tuple._2))
    );
  }

  @Get(value = "/{id}/rating", produces = MediaType.APPLICATION_JSON)
  public HttpResponse<Optional<Rating>> getRating(@PathVariable("id") String id) {

    return HttpResponse.ok(
        service.getRating(id).map(Rating::fromDomain)
    );
  }
}
