package rise.trader.orderbook.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import rise.trader.orderbook.common.pojo.OrderStatus;
import rise.trader.orderbook.consumer.dao.Order;
import rise.trader.orderbook.consumer.service.OrderBookQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping ("/api/v1/order")
@Controller
@Slf4j
public class OrderBookQueryApi {

    private OrderBookQueryService orderBookQueryService;

    @Autowired
    public OrderBookQueryApi(OrderBookQueryService orderBookQueryService) {
        this.orderBookQueryService = orderBookQueryService;
    }

    @GetMapping
    public ResponseEntity fetchOrders(@RequestParam (name = "status") OrderStatus status) {

        log.info("Fetch orders request for the order status - {}", status);

        List<Order> orders;

        if (status == null) {
            orders = orderBookQueryService.fetchOrders();
        } else {
            orders = orderBookQueryService.fetchOrders(status);
        }
        return ResponseEntity.ok(orders);
    }

    @GetMapping ("/pending")
    public ResponseEntity fetchPendingOrders() {

        log.info("Fetch pending orders request");

        List<Order> orders = orderBookQueryService.fetchPendingOrders();
        return ResponseEntity.ok(orders);
    }
}
