package rise.trader.orderbook.consumer.service.impl;

import rise.trader.orderbook.common.pojo.OrderStatus;
import rise.trader.orderbook.consumer.dao.Order;
import rise.trader.orderbook.consumer.repository.OrderRepository;
import rise.trader.orderbook.consumer.service.OrderBookQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderBookQueryServiceImpl implements OrderBookQueryService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderBookQueryServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> fetchOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> fetchOrders(OrderStatus status) {
        return orderRepository.findAllByStatus(status);
    }

    @Override
    public List<Order> fetchPendingOrders() {
        return orderRepository.findPendingOrders();
    }
}
