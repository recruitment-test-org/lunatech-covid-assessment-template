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
import lunatech.imdb.controllers.dtos.Title;

@MicronautTest
public class PeopleControllerTest {

  @Inject
  EmbeddedServer server;

  @Inject
  @Client("/")
  HttpClient client;

  @Test
  void testGetPeopleById_200() {
    String response = client.toBlocking()
        .retrieve(HttpRequest.GET("/people/nm0000021"));
    
    assertEquals(
      "{\"id\":\"nm0000021\",\"name\":\"Joan Fontaine\",\"birthYear\":\"1917-01-01\",\"deathYear\":\"2013-01-01\",\"profession\":[\"actress\",\"soundtrack\",\"producer\"]}",
      response
    );
  }

  @Test
  void testGetPeopleById_404() {
    try {
      client.toBlocking().exchange(HttpRequest.GET("/people/xx0000021"));
      fail("Getting an non-existing person should return 404");
    } catch (HttpClientResponseException ex) {
      assertEquals(404, ex.getResponse().code());
    }
  }

  @Test
  void testGetKnownForTitles_200() {
    String response = client.toBlocking()
        .retrieve(HttpRequest.GET("/people/nm0000021/known-for-titles"));
    
    assertEquals(
      "[{\"id\":\"tt0032976\",\"type\":\"movie\",\"name\":\"Rebecca\",\"originalName\":\"Rebecca\",\"startYear\":\"1940-01-01\",\"runtimeInMinutes\":130,\"genres\":[\"Drama\",\"Mystery\",\"Romance\"]},{\"id\":\"tt0034248\",\"type\":\"movie\",\"name\":\"Suspicion\",\"originalName\":\"Suspicion\",\"startYear\":\"1941-01-01\",\"runtimeInMinutes\":99,\"genres\":[\"Film-Noir\",\"Mystery\",\"Thriller\"]},{\"id\":\"tt0039504\",\"type\":\"movie\",\"name\":\"Ivy\",\"originalName\":\"Ivy\",\"startYear\":\"1947-01-01\",\"runtimeInMinutes\":99,\"genres\":[\"Crime\",\"Drama\",\"Thriller\"]},{\"id\":\"tt0040536\",\"type\":\"movie\",\"name\":\"Letter from an Unknown Woman\",\"originalName\":\"Letter from an Unknown Woman\",\"startYear\":\"1948-01-01\",\"runtimeInMinutes\":87,\"genres\":[\"Drama\",\"Romance\"]}]",
      response
    );
  }

  @Test
  void testGetKnownForTitles_404() {
    try {
      client.toBlocking().exchange(HttpRequest.GET("/people/xx0000021/known-for-titles"));
      fail("Getting an non-existing person should return 404");
    } catch (HttpClientResponseException ex) {
      assertEquals(404, ex.getResponse().code());
    }
  }

  @Test
  void testGetKnownForTitles_204() {
    HttpResponse<List<Title>> response = client.toBlocking()
        .exchange(HttpRequest.GET("/people/nm10154221/known-for-titles"));
    
    assertEquals(204, response.code());
    assertEquals(List.of(), response.body());
  }
}
