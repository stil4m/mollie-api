Travis says: <img src="https://api.travis-ci.org/stil4m/mollie-api.png" />

# Mollie API Java

## License

The source code is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

## Java

This library requires Java 8.

## Dependencies

```
org.apache.httpcomponents:httpclient:4.3.X
com.fasterxml.jackson.core:jackson-annotations:2.6.X
com.fasterxml.jackson.core:jackson-databind:2.6.X
```

## Maven

This library is available on my [personal maven repository](https://github.com/stil4m/maven-repository), which is hosted on GitHub.

To use this with library with Maven, add the following snippets to your `pom.xml` file:

```
...
<repository>
    <id>stil4m-releases</id>
    <name>stil4m-releases</name>
    <url>https://github.com/stil4m/maven-repository/raw/master/releases/</url>
</repository>

...

<depencency>
    <groupId>nl.stil4m</groupId>
    <artifactId>mollie-api</artifactId>
    <version>1.3.0</version>
</depencency>

...
```

## Build the JAR

To build the `JAR` you can run `mvn install`.

