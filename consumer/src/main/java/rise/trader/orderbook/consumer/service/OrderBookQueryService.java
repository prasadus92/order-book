package rise.trader.orderbook.consumer.service;

import rise.trader.orderbook.common.pojo.OrderStatus;
import rise.trader.orderbook.consumer.dao.Order;

import java.util.List;

public interface OrderBookQueryService {

    List<Order> fetchOrders();

    List<Order> fetchOrders(OrderStatus status);

    List<Order> fetchPendingOrders();
}
