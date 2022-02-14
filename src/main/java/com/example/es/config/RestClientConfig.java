package com.example.es.config;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * User: Leehao
 * Date: 2022/2/14
 * Time: 10:18
 * Description:
 */
@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    @Value("${es.host}")
    private String esHost;

    //RequestOptions类包含请求的一些部分，这些部分应该在同一个应用程序中的多个请求之间共享。你可以创建一个单实例，并在所有请求之间共享:
    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(esHost)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
