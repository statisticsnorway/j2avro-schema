# j2avro-schema

A utility CLI that transforms Java POJOs to Avro schema.

## Usage
```shell
java -cp lib/my-pojos.jar:j2avro-schema-x.x.x.jar no.ssb.avro.j2avro.Cli --root-type no.ssb.blah.MyRoot > avro-schema.json
```

## Scenario: OpenAPI to Avro schema

A typical scenario would be that you want to transform OpenAPI specifications to Avro Schema.
You could use the [openapi-generator-cli](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator-cli)
to generate the Java POJOs and then feed the generated classes to j2avro-schema.

Example of how to use the openapi-generator-cli.

#### Prerequisites
Install openapi-generator:
```shell
brew install openapi-generator
```

#### Generate java project from OpenAPI specification
```shell
openapi-generator generate \
  --input-spec blah-openapi-spec.yml \
  --generator-name java \
  --output blah/ \
  --model-package no.ssb.openapi.blah \
  --group-id no.ssb.openapi.blah \
  --artifact-id blah \
  --artifact-version 1.0.0-SNAPSHOT
```

#### Build jar file with POJOs
```shell
mvn clean package -f blah/pom.xml
cp blah/target/blah-1.0.0-SNAPSHOT.jar .
```

#### Generate avro schema from POJOs
```shell
java -cp lib/blah-1.0.0-SNAPSHOT.jar:j2avro-schema-x.x.x.jar no.ssb.avro.j2avro.Cli --root-type no.ssb.blah.SomeRoot > blah-avro-schema.json
```
