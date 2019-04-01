package rise.trader.orderbook.publisher.controller;

import lombok.extern.slf4j.Slf4j;
import rise.trader.orderbook.common.command.*;
import rise.trader.orderbook.common.pojo.OrderStatus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping ("/api/v1/order")
@Controller
@Slf4j
public class OrderBookApi {

    private final CommandGateway commandGateway;

    private final EventStore eventStore;

    @Autowired
    public OrderBookApi(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @GetMapping ("/{id}/events")
    public List<Object> getEvents(@PathVariable String id) {
        log.info("Fetch events request for ID - {}", id);
        return eventStore.readEvents(id).asStream().map(s -> s.getPayload()).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity placeOrder(@RequestBody PlaceOrderCommand command) {
        String tradeId = command.getTradeId();
        if (StringUtils.isEmpty(tradeId)) {
            throw new IllegalArgumentException("Trade ID can never be null");
        }
        log.info("Place Order Command for Trade ID - {}", tradeId);

        commandGateway.send(command);
        return ResponseEntity.ok().build();
    }

    @PutMapping (path = "/{id}")
    public ResponseEntity updateStatus(@PathVariable String id, @RequestParam (name = "status") OrderStatus status) {
        log.info("Update Status request for Trade ID - {}", id);

        if (StringUtils.isEmpty(id) || status == null) {
            throw new IllegalArgumentException("Incorrect request, both ID and status have to be present");
        }

        switch (status) {
            case ORDER_PLACED:
                // ToDo: Revisit this.
                log.error("Incorrect status update request, PUT is not allowed for PlaceOrder");
                return ResponseEntity.badRequest().body("Use POST for placing a new Order");
            case ORDER_CREATED:
                commandGateway.send(new CreateOrderCommand(id, status));
                break;
            case ORDER_FILLED:
                commandGateway.send(new FillOrderCommand(id, status));
                break;
            case ORDER_PARTIALLY_FILLED:
                commandGateway.send(new PartiallyFillOrderCommand(id, status));
                break;
            case ORDER_SUBMITTED:
                commandGateway.send(new SubmitOrderCommand(id, status));
                break;
            case ORDER_CANCELLED:
                commandGateway.send(new CancelOrderCommand(id, status));
                break;
            case ORDER_CANCEL_SUBMITTED:
                return cancelOrder(id);
            default:
                throw new IllegalArgumentException("Unknown order status");
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity cancelOrder(@PathVariable String id) {
        log.info("Cancel Order request for Trade ID - {}", id);

        commandGateway.send(new SubmitCancelOrderCommand(id));
        return ResponseEntity.ok().build();
    }
}
