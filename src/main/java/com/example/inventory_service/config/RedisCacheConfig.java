package com.example.inventory_service.config;

import com.example.inventory_service.dto.InventoryResponse;
import com.example.inventory_service.dto.ProductResponse;
import tools.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Configuration
public class RedisCacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory,
                                     ObjectMapper objectMapper,
                                     @Value("${spring.cache.redis.time-to-live:10m}") Duration ttl) {
        RedisCacheConfiguration base = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(ttl)
                .disableCachingNullValues();

        JacksonJsonRedisSerializer<ProductResponse> productSerializer =
                new JacksonJsonRedisSerializer<>(objectMapper, ProductResponse.class);
        JacksonJsonRedisSerializer<InventoryResponse> inventorySerializer =
                new JacksonJsonRedisSerializer<>(objectMapper, InventoryResponse.class);
        JacksonJsonRedisSerializer<List<ProductResponse>> productListSerializer =
                new JacksonJsonRedisSerializer<>(objectMapper,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, ProductResponse.class));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(base)
                .withInitialCacheConfigurations(Map.of(
                        "product", typed(base, productSerializer),
                        "productList", typed(base, productListSerializer),
                        "inventory", typed(base, inventorySerializer)))
                .build();
    }

    private <T> RedisCacheConfiguration typed(RedisCacheConfiguration base,
                                                JacksonJsonRedisSerializer<T> serializer) {
        return base.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(serializer));
    }
}
