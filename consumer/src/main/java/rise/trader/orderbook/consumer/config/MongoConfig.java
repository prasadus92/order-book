package rise.trader.orderbook.consumer.config;

import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.MongoClient;

import java.io.IOException;

@Configuration
public class MongoConfig {

    private static final String MONGO_DB_URL = "localhost";

    private static final String MONGO_DB = "test";

    private static final int MONGO_DB_PORT = 60444;

    @Bean
    public MongoClient mongoClient() throws IOException {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(MONGO_DB_URL);
        mongo.setPort(MONGO_DB_PORT);
        return mongo.getObject();
    }

    @Bean
    public MongoTemplate mongoTemplate() throws IOException {
        return new MongoTemplate(mongoClient(), MONGO_DB);
    }
}
