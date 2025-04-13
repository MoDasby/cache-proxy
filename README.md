# Cache Proxy

A simple and efficient **caching proxy server** built with **Spring Boot** and **WebFlux**, using an in-memory **LRU cache** to store GET request responses.

This project is based on the [Caching Server](https://roadmap.sh/projects/caching-server) idea from [roadmap.sh](https://roadmap.sh/), intended as a practical learning exercise.

---

## Tech Stack

- Java 21+
- Spring Boot
- Spring WebFlux (WebClient)
- Custom thread-safe LRU Cache
- JUnit 5 + WebTestClient
- Docker
- Maven

---

## How It Works

- Intercepts incoming **GET** requests.
- If the request is cached, the response is returned immediately with `X-Cache: HIT`.
- Otherwise, it fetches the response from the origin server, stores it in the LRU cache, and returns it with `X-Cache: MISS`.

---

## How to run

### Configuration

In `application.properties`, set your origin server and the cache capacity(default to 100):

```properties
origin=https://your-origin-api.com
cache.capacity=100
```

### Running with Docker

1. Clone the repository
```bash
git clone git@github.com:modasby/cache-proxy.git
cd cache-proxy
```

2. Build and run the container
```bash
docker build -t cache-proxy:latest .
docker run -p 8080:8080 cache-proxy:latest
```
### Running Tests

You can run the tests using Maven:

```bash
./mvnw test
```




