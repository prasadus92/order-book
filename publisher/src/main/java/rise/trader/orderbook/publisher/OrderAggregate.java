package rise.trader.orderbook.publisher;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rise.trader.orderbook.common.command.*;
import rise.trader.orderbook.common.event.OrderCreatedEvent;
import rise.trader.orderbook.common.event.OrderDeletedEvent;
import rise.trader.orderbook.common.event.OrderUpdatedEvent;
import rise.trader.orderbook.common.pojo.Account;
import rise.trader.orderbook.common.pojo.OrderStatus;
import rise.trader.orderbook.common.pojo.Side;
import rise.trader.orderbook.common.pojo.Symbol;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
@Slf4j
public class OrderAggregate {

    @AggregateIdentifier
    private String tradeId;

    private Instant timestamp;

    private String uptickOrderId;

    private String replyTo;

    private Account account;

    private Symbol symbol;

    private BigDecimal quantity;

    private Side side;

    private BigDecimal limit;

    private BigDecimal stop;

    @JsonProperty (value = "status")
    private OrderStatus status;

    @CommandHandler
    public OrderAggregate(PlaceOrderCommand placeOrderCommand) {

        Assert.hasLength(placeOrderCommand.getTradeId(), "Trade ID cannot be null");

        apply(new OrderCreatedEvent(placeOrderCommand));
    }

    @CommandHandler
    public void handle(CreateOrderCommand createOrderCommand) {

        Assert.hasLength(createOrderCommand.tradeId, "Trade ID cannot be null");

        apply(new OrderUpdatedEvent(createOrderCommand));
    }

    @CommandHandler
    public void handle(FillOrderCommand fillOrderCommand) {

        Assert.hasLength(fillOrderCommand.tradeId, "Trade ID cannot be null");

        apply(new OrderUpdatedEvent(fillOrderCommand));
    }

    @CommandHandler
    public void handle(PartiallyFillOrderCommand partiallyFillOrderCommand) {

        Assert.hasLength(partiallyFillOrderCommand.tradeId, "Trade ID cannot be null");

        apply(new OrderUpdatedEvent(partiallyFillOrderCommand));
    }

    @CommandHandler
    public void handle(SubmitOrderCommand submitOrderCommand) {

        Assert.hasLength(submitOrderCommand.tradeId, "Trade ID cannot be null");

        apply(new OrderUpdatedEvent(submitOrderCommand));
    }

    @CommandHandler
    public void handle(CancelOrderCommand cancelOrderCommand) {

        Assert.hasLength(cancelOrderCommand.tradeId, "Trade ID cannot be null");

        apply(new OrderUpdatedEvent(cancelOrderCommand));
    }

    @CommandHandler
    public void handle(SubmitCancelOrderCommand submitCancelOrderCommand) {

        String tradeId = submitCancelOrderCommand.getTradeId();
        Assert.hasLength(tradeId, "Trade ID cannot be null");

        apply(new OrderDeletedEvent(tradeId));
    }

    @EventSourcingHandler
    private void on(OrderCreatedEvent orderCreatedEvent) {

        String tradeId = orderCreatedEvent.getTradeId();

        log.info("Updating the aggregate on OrderCreatedEvent - {}", tradeId);

        this.tradeId = tradeId;
        this.account = orderCreatedEvent.getAccount();
        this.limit = orderCreatedEvent.getLimit();
        this.status = orderCreatedEvent.getOrderStatus();
        this.quantity = orderCreatedEvent.getQuantity();
        this.replyTo = orderCreatedEvent.getReplyTo();
        this.side = orderCreatedEvent.getSide();
        this.stop = orderCreatedEvent.getStop();
        this.symbol = orderCreatedEvent.getSymbol();
        this.timestamp = orderCreatedEvent.getTimestamp();
        this.uptickOrderId = orderCreatedEvent.getUptickOrderId();
    }

    @EventSourcingHandler
    private void on(OrderUpdatedEvent orderUpdatedEvent) {

        log.info("Updating the aggregate on OrderUpdatedEvent - {}", orderUpdatedEvent.getTradeId());

        this.status = orderUpdatedEvent.getStatus();
    }

    @EventSourcingHandler
    private void on(OrderDeletedEvent orderDeletedEvent) {

        log.info("Updating the aggregate on OrderDeletedEvent - {}", orderDeletedEvent.getTradeId());

        this.status = OrderStatus.ORDER_CANCEL_SUBMITTED;
    }
}
