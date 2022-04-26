# quarkus-hibernate-search-starter-demo Project

This project uses Quarkus, Hibernate Search and Elasticsearch to showcase a web application with full-text search features.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Before running the application

The application needs Elasticsearch to be available on localhost:9200.
With Docker, you can start Elasticsearch this way:

```shell script
docker run --rm -ti --ulimit memlock=-1:-1 --memory-swappiness=0 \
    --name acme_elasticsearch -e xpack.security.enabled=false -e discovery.type=single-node \
    -e "ES_JAVA_OPTS=-Xms1g -Xmx1g" -p 9200:9200 elastic/elasticsearch:7.16.3
```

In development mode, Quarkus will automatically start a PostgreSQL instance thanks to
[Dev Services](https://quarkus.io/guides/dev-services#databases).

If you want to run the application in production mode, you will need to make PostgreSQL
available on localhost:5432.
With Docker, you can start PostgreSQL this way:

```shell script
docker run -ti --rm --ulimit memlock=-1:-1 --memory-swappiness=0 \
    --name acme_postgresql -e POSTGRES_USER=acme -e POSTGRES_PASSWORD=acme \
    -e POSTGRES_DB=acme_db -p 5432:5432 postgres:13.1
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-hibernate-search-starter-demo-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- Hibernate Search ([guide](https://quarkus.io/guides/hibernate-search-orm-elasticsearch), [reference documentation](https://docs.jboss.org/hibernate/search/6.1/reference/en-US/html_single/)): Automatically index your Hibernate entities in Elasticsearch
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code for Hibernate ORM via the active record or the repository pattern
