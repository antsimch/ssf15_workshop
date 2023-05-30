package sg.edu.nus.iss.ssf16_workshop.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisConfig {
    
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private Optional<Integer> redisPort;

    @Value("${spring.data.redis.username}")
    private String redisUsername;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Bean
    @Scope("singleton")
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisStandaloneConfiguration config =
            new RedisStandaloneConfiguration(redisHost, redisPort.get());

        if (!redisUsername.isEmpty() && !redisPassword.isEmpty()) {
            config.setUsername(redisUsername);
            config.setPassword(redisPassword);
        }

        config.setDatabase(0);
        final JedisClientConfiguration client
                        = JedisClientConfiguration.builder().build();
        
        final JedisConnectionFactory factory = new JedisConnectionFactory(config, client);

        factory.afterPropertiesSet();
        RedisTemplate<String, Object> r
                        = new RedisTemplate<>();
        r.setConnectionFactory(factory);
        r.setKeySerializer(new StringRedisSerializer());
        r.setValueSerializer(new StringRedisSerializer());

        return r;
    }
}
