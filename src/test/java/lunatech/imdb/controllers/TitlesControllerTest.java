package lunatech.imdb.controllers;

import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import jakarta.inject.Inject;
import lunatech.imdb.controllers.dtos.Cast;

@MicronautTest
public class TitlesControllerTest {

  @Inject
  EmbeddedServer server;

  @Inject
  @Client("/")
  HttpClient client;

  @Test
  void testGetTitleById_200() {
    String response = client.toBlocking()
        .retrieve(HttpRequest.GET("/titles/tt0095016"));
    
    assertEquals(
      "{\"id\":\"tt0095016\",\"type\":\"movie\",\"name\":\"Die Hard\",\"originalName\":\"Die Hard\",\"startYear\":\"1988-01-01\",\"runtimeInMinutes\":132,\"genres\":[\"Action\",\"Thriller\"]}",
      response
    );
  }

  @Test
  void testGetTitleById_404() {
    try {
      client.toBlocking().exchange(HttpRequest.GET("/titles/xx0095016"));
      fail("Getting an non-existing title should return 404");
    } catch (HttpClientResponseException ex) {
      assertEquals(404, ex.getResponse().code());
    }
  }

  @Test
  void testGetTitleRating_200() {
    String response = client.toBlocking()
        .retrieve(HttpRequest.GET("/titles/tt0095016/rating"));
    
    assertEquals(
      "{\"averageRating\":8.2,\"numberOfVotes\":788913}",
      response
    );
  }

  @Test
  void testGetTitleRating_404_NoTitle() {
    try {
      client.toBlocking().retrieve(HttpRequest.GET("/titles/xx0000812/rating"));
      fail("Getting an non-existing title should return 404");
    } catch (HttpClientResponseException ex) {
      assertEquals(404, ex.getResponse().code());
    }
  }

  @Test
  void testGetTitleRating_404_NoRating() {
    try {
      client.toBlocking().retrieve(HttpRequest.GET("/titles/tt0000812/rating"));
      fail("Getting an non-existing rating should return 404");
    } catch (HttpClientResponseException ex) {
      assertEquals(404, ex.getResponse().code());
    }
  }

  @Test
  void testGetTitleCrew_200() {
    String response = client.toBlocking()
        .retrieve(HttpRequest.GET("/titles/tt0095016/crew"));
    
    assertEquals(
      "{\"directors\":[{\"id\":\"nm0001532\",\"name\":\"John McTiernan\",\"birthYear\":\"1951-01-01\",\"profession\":[\"director\",\"producer\",\"writer\"]}],\"writers\":[{\"id\":\"nm0211823\",\"name\":\"Steven E. de Souza\",\"birthYear\":\"1947-01-01\",\"profession\":[\"writer\",\"producer\",\"miscellaneous\"]},{\"id\":\"nm0835732\",\"name\":\"Jeb Stuart\",\"birthYear\":\"1956-01-01\",\"profession\":[\"writer\",\"producer\",\"director\"]},{\"id\":\"nm0861636\",\"name\":\"Roderick Thorp\",\"birthYear\":\"1936-01-01\",\"deathYear\":\"1999-01-01\",\"profession\":[\"writer\",\"actor\",\"miscellaneous\"]}]}",
      response
    );
  }

  @Test
  void testGetTitleCrew_404_NoTitle() {
    try {
      client.toBlocking().retrieve(HttpRequest.GET("/titles/xx0000812/crew"));
      fail("Getting an non-existing title should return 404");
    } catch (HttpClientResponseException ex) {
      assertEquals(404, ex.getResponse().code());
    }
  }

  @Test
  void testGetTitleCast_200() {
    String response = client.toBlocking()
        .retrieve(HttpRequest.GET("/titles/tt0095016/cast"));
    
    assertEquals(
      "[{\"person\":{\"id\":\"nm0000246\",\"name\":\"Bruce Willis\",\"birthYear\":\"1955-01-01\",\"profession\":[\"actor\",\"soundtrack\",\"producer\"]},\"category\":\"actor\"},{\"person\":{\"id\":\"nm0000614\",\"name\":\"Alan Rickman\",\"birthYear\":\"1946-01-01\",\"deathYear\":\"2016-01-01\",\"profession\":[\"actor\",\"soundtrack\",\"writer\"]},\"category\":\"actor\"},{\"person\":{\"id\":\"nm0000889\",\"name\":\"Bonnie Bedelia\",\"birthYear\":\"1948-01-01\",\"profession\":[\"actress\",\"soundtrack\"]},\"category\":\"actress\"},{\"person\":{\"id\":\"nm0001532\",\"name\":\"John McTiernan\",\"birthYear\":\"1951-01-01\",\"profession\":[\"director\",\"producer\",\"writer\"]},\"category\":\"director\"},{\"person\":{\"id\":\"nm0001817\",\"name\":\"Reginald VelJohnson\",\"birthYear\":\"1952-01-01\",\"profession\":[\"actor\",\"writer\",\"soundtrack\"]},\"category\":\"actor\"},{\"person\":{\"id\":\"nm0005428\",\"name\":\"Joel Silver\",\"birthYear\":\"1952-01-01\",\"profession\":[\"producer\",\"actor\",\"miscellaneous\"]},\"category\":\"producer\",\"job\":\"producer\"},{\"person\":{\"id\":\"nm0211823\",\"name\":\"Steven E. de Souza\",\"birthYear\":\"1947-01-01\",\"profession\":[\"writer\",\"producer\",\"miscellaneous\"]},\"category\":\"writer\",\"job\":\"screenplay by\"},{\"person\":{\"id\":\"nm0330383\",\"name\":\"Lawrence Gordon\",\"birthYear\":\"1936-01-01\",\"profession\":[\"producer\",\"writer\",\"miscellaneous\"]},\"category\":\"producer\",\"job\":\"producer\"},{\"person\":{\"id\":\"nm0835732\",\"name\":\"Jeb Stuart\",\"birthYear\":\"1956-01-01\",\"profession\":[\"writer\",\"producer\",\"director\"]},\"category\":\"writer\",\"job\":\"screenplay by\"},{\"person\":{\"id\":\"nm0861636\",\"name\":\"Roderick Thorp\",\"birthYear\":\"1936-01-01\",\"deathYear\":\"1999-01-01\",\"profession\":[\"writer\",\"actor\",\"miscellaneous\"]},\"category\":\"writer\",\"job\":\"based on the novel by\"}]",
      response
    );
  }

  @Test
  void testGetTitleCast_404_NoTitle() {
    try {
      client.toBlocking().retrieve(HttpRequest.GET("/titles/xx0095016/cast"));
      fail("Getting an non-existing title should return 404");
    } catch (HttpClientResponseException ex) {
      assertEquals(404, ex.getResponse().code());
    }
  }
 
  @Test
  void testGetTitleCast_204_NoCast() {
    HttpResponse<List<Cast>> response = client.toBlocking()
        .exchange(HttpRequest.GET("/titles/tt0098828/cast"));
    
    
    assertEquals(204, response.code());
    assertEquals(List.of(), response.body());
  }

}
