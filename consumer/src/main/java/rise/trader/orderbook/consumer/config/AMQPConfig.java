package rise.trader.orderbook.consumer.config;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.amqp.eventhandling.DefaultAMQPMessageConverter;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.serialization.Serializer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.rabbitmq.client.Channel;

@Slf4j
@Configuration
public class AMQPConfig {

    @Bean
    public SpringAMQPMessageSource ordersQueue(Serializer serializer) {
        return new SpringAMQPMessageSource(new DefaultAMQPMessageConverter(serializer)) {

            @RabbitListener (queues = "Orders")
            @Override
            public void onMessage(Message message, Channel channel) {
                log.info("A new message received from the Orders Queue");
                super.onMessage(message, channel);
            }
        };
    }
}
