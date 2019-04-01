package rise.trader.orderbook.publisher.repository;

import rise.trader.orderbook.publisher.OrderAggregate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAggregateRepository extends MongoRepository<OrderAggregate, String> {
}
