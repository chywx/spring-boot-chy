package cn.chendahai.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("spring.redis")
@ConditionalOnProperty("spring.redis.host")
@Data
public class RedissonConfig {


    private int port;

    private String host;

    private String password;


    @Bean
    public RedissonClient getRedissonClient() {

        Config config = new Config();
        String prefix = "redis://";
        config.useSingleServer()
                .setAddress(prefix + this.getHost() + ":" + this.getPort())
                .setPassword(this.getPassword());
        return Redisson.create(config);
    }

}
