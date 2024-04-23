package lunatech.imdb.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Inject;
import lunatech.imdb.controllers.dtos.Person;
import lunatech.imdb.controllers.dtos.Title;
import lunatech.imdb.services.PeopleService;

import java.util.List;
import java.util.Optional;

@Controller("/people")
public class PeopleController {

  @Inject
  public PeopleService service;

  @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
  public HttpResponse<Optional<Person>> getPerson(@PathVariable("id") String id) {
    return HttpResponse.ok(
        service.getPerson(id).map(Person::fromDomain)
    );
  }

  @Get(value = "/{id}/known-for-titles", produces = MediaType.APPLICATION_JSON)
  public HttpResponse<List<Title>> getKnownForTitles(@PathVariable("id") String id) {
    return HttpResponse.ok(
        service.getKnownForTitles(id).stream()
            .map(Title::fromDomain)
            .sorted(Title.compareById)
            .toList()
    );
  }

}
