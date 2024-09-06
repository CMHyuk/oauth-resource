package com.oauth.resource.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.images.PullPolicy;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@Testcontainers
public class SpringElasticSearchTestContainer {

    static final DockerImageName myImage = DockerImageName.parse("elasticsearch:7.9.3").asCompatibleSubstituteFor("docker.elastic.co/elasticsearch/elasticsearch");
    static final ElasticsearchContainer ELASTICSEARCH_CONTAINER = new ElasticsearchContainer(myImage)
            .withEnv("ES_JAVA_OPTS", "-Xms512m -Xmx512m")
            .withEnv("discovery.type", "single-node");
    static final DockerImageName AUTHORIZATION_IMAGE = DockerImageName.parse("scr.softcamp.co.kr/secaas/ojt-minhyeok-authorization:latest");
    static final DockerImageName REDIS_IMAGE = DockerImageName.parse("redis");

    public static final GenericContainer<?> AUTHORIZATION_CONTAINER;
    public static final GenericContainer<?> REDIS_CONTAINER;

    static {
        ELASTICSEARCH_CONTAINER.start();

        // Redis
        REDIS_CONTAINER = new GenericContainer<>(REDIS_IMAGE)
                .withExposedPorts(6379);
        REDIS_CONTAINER.setWaitStrategy(new WaitAllStrategy());
        REDIS_CONTAINER.start();

        // Authorization
        AUTHORIZATION_CONTAINER = new GenericContainer<>(AUTHORIZATION_IMAGE)
                .withEnv("spring.profiles.active", "dev")
                .withEnv("ELASTIC_HOST", ELASTICSEARCH_CONTAINER.getHost())
                .withEnv("ELASTIC_PORT", ELASTICSEARCH_CONTAINER.getFirstMappedPort().toString())

                .withEnv("REDIS_HOST", REDIS_CONTAINER.getHost())
                .withEnv("REDIS_PORT", REDIS_CONTAINER.getFirstMappedPort().toString())
                .withEnv("REDIS_PASSWORD", "1234")

                .withExposedPorts(9000)
                .withImagePullPolicy(PullPolicy.alwaysPull());

        AUTHORIZATION_CONTAINER.addExposedPorts(9000);

        AUTHORIZATION_CONTAINER.setWaitStrategy(new WaitAllStrategy());
        AUTHORIZATION_CONTAINER.start();

        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
        ELASTICSEARCH_CONTAINER.followOutput(logConsumer);
        AUTHORIZATION_CONTAINER.followOutput(logConsumer);
        REDIS_CONTAINER.followOutput(logConsumer);
    }

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("elasticsearch.port", ELASTICSEARCH_CONTAINER::getFirstMappedPort);
        registry.add("elasticsearch.host", ELASTICSEARCH_CONTAINER::getHost);

        registry.add("spring.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.redis.port", REDIS_CONTAINER::getFirstMappedPort);

        log.info("authorization host : {}", AUTHORIZATION_CONTAINER.getHost());
        log.info("authorization port : {}", AUTHORIZATION_CONTAINER.getFirstMappedPort().toString());
    }
}
