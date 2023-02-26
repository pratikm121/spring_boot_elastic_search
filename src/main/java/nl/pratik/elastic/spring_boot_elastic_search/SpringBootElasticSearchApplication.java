package nl.pratik.elastic.spring_boot_elastic_search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootElasticSearchApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringBootElasticSearchApplication.class, args);
    }

}
