package rise.trader.orderbook.consumer;

import lombok.extern.slf4j.Slf4j;
import rise.trader.orderbook.common.event.OrderCreatedEvent;
import rise.trader.orderbook.common.event.OrderDeletedEvent;
import rise.trader.orderbook.common.event.OrderUpdatedEvent;
import rise.trader.orderbook.common.pojo.OrderStatus;
import rise.trader.orderbook.consumer.dao.Order;
import rise.trader.orderbook.consumer.repository.OrderRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@ProcessingGroup ("orderBook")
@Slf4j
@Component
public class OrderEventProcessor {

    private OrderRepository orderRepository;

    private MongoTemplate mongoTemplate;

    @Autowired
    public OrderEventProcessor(OrderRepository orderRepository, MongoTemplate mongoTemplate) {
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @EventHandler
    private void on(OrderCreatedEvent event) {

        String tradeId = event.getTradeId();
        log.info("OrderCreatedEvent - {}", tradeId);

        Order order = Order.builder()
                           .tradeId(tradeId)
                           .account(event.getAccount())
                           .limit(event.getLimit())
                           .status(event.getOrderStatus())
                           .quantity(event.getQuantity())
                           .replyTo(event.getReplyTo())
                           .side(event.getSide())
                           .stop(event.getStop())
                           .symbol(event.getSymbol())
                           .timestamp(event.getTimestamp())
                           .uptickOrderId(event.getUptickOrderId())
                           .build();
        orderRepository.save(order);
    }

    @EventHandler
    private void on(OrderUpdatedEvent event) {

        String tradeId = event.getTradeId();
        log.info("OrderUpdatedEvent - {}", tradeId);

        updateStatus(tradeId, event.getStatus());
    }

    @EventHandler
    private void on(OrderDeletedEvent event) {

        String tradeId = event.getTradeId();
        log.info("OrderDeletedEvent - {}", tradeId);

        // ToDo: Revisit this.
        updateStatus(tradeId, OrderStatus.ORDER_CANCEL_SUBMITTED);
    }

    // ToDo: Error handling on update failure, how to recover/retry.
    public void updateStatus(String id, OrderStatus status) {

        Order order = mongoTemplate.findOne(
            Query.query(Criteria.where("_id").is(id)), Order.class);

        if (order == null) {
            throw new RuntimeException("Order with the provided Trade ID doesn't exist!");
        }
        order.setStatus(status);
        orderRepository.save(order);
    }
}
