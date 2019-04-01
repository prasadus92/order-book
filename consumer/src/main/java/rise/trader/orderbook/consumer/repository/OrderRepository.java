package rise.trader.orderbook.consumer.repository;

import rise.trader.orderbook.common.pojo.OrderStatus;
import rise.trader.orderbook.consumer.dao.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findAll();

    List<Order> findAllByStatus(OrderStatus status);

    @Query ("{'$or':[ {'status':'ORDER_SUBMITTED'}, {'status':'ORDER_PLACED'} ] }")
    List<Order> findPendingOrders();
}
