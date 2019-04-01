package rise.trader.orderbook.common.event;

import lombok.Getter;
import rise.trader.orderbook.common.command.PlaceOrderCommand;
import rise.trader.orderbook.common.pojo.Account;
import rise.trader.orderbook.common.pojo.OrderStatus;
import rise.trader.orderbook.common.pojo.Side;
import rise.trader.orderbook.common.pojo.Symbol;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
public class OrderCreatedEvent {

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

    private OrderStatus orderStatus;

    public OrderCreatedEvent(PlaceOrderCommand command) {
        this.tradeId = command.getTradeId();
        this.account = command.getAccount();
        this.limit = command.getLimit();
        this.orderStatus = OrderStatus.ORDER_PLACED;
        this.quantity = command.getQuantity();
        this.replyTo = command.getReplyTo();
        this.side = command.getSide();
        this.stop = command.getStop();
        this.symbol = command.getSymbol();
        this.timestamp = command.getTimestamp();
        this.uptickOrderId = command.getUptickOrderId();
    }
}
