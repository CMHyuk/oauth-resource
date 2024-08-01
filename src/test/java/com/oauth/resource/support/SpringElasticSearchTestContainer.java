package com.oauth.resource.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@Testcontainers
class SpringElasticSearchTestContainer {

    static final ElasticsearchContainer ELASTIC_SEARCH_CONTAINER;

    static {
        DockerImageName myImage = DockerImageName.parse("elasticsearch:7.9.3").asCompatibleSubstituteFor("docker.elastic.co/elasticsearch/elasticsearch");
        ELASTIC_SEARCH_CONTAINER = new ElasticsearchContainer(myImage)
                .withEnv("ES_JAVA_OPTS", "-Xms512m -Xmx512m")
                .withEnv("discovery.type", "single-node");

        ELASTIC_SEARCH_CONTAINER.start();

        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
        ELASTIC_SEARCH_CONTAINER.followOutput(logConsumer);
    }

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("elasticsearch.port", ELASTIC_SEARCH_CONTAINER::getFirstMappedPort);
        registry.add("elasticsearch.host", ELASTIC_SEARCH_CONTAINER::getHost);
    }
}
