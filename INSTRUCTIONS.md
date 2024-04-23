# Lunatech IMDB IRL Assessment Instructions

You have been invited to a Live Coding Assessment! To make sure you're ready,
and the session goes as smooth as possible, please perform the following steps:

**It is very important that you have you computer ready for the interview, if
you encounter any issues please let us know as soon as possible.**

**If your computer is not ready before the interview, please contact us to
reschedule it**

## Installation

The assessment requires Docker, JDK 17, we _strongly_ suggest that you have
[SDKMan](https://sdkman.io/) installed.

```bash
docker pull ghcr.io/lunatech-labs/lunatech-imdb-assessment-db:1.0-xs

sdk install java 17.0.8-librca
```

## Database Setup

The image that you pulled is a PostgreSQL image containing a database schema,
with data in it.

Create a Docker Compose file just like this one:

```yaml
version: '2.1'

services:
  postgres:
    image: ghcr.io/lunatech-labs/lunatech-imdb-assessment-db:1.0-xs
    container_name: lunatech-imdb-assessment-db
    environment:
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
      - POSTGRES_DB=lunatech_imdb
    ports:
      - "5432:5432"
    networks:
      - default
    healthcheck:
      test: [ "CMD-SHELL", "pg_isready -U postgres" ]
      interval: 10s
      timeout: 5s
      retries: 5
    logging:
      driver: none
```

After this run the file with:

```bash
docker-compose up
```

The initialization process should start after a few moments. Loading all the
data can take up to 30 minutes.

After the process has finished, you should see a message in the console. You
can stop the container if you want.

**IMPORTANT:** After you initialized the database **do not** remove any volumes
or prune docker. You should be able to re-run `docker-compose up` again, skip
the initialization process, and get a database ready to go.

If this is not the case, please contact us before the session takes place.
