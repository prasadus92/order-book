package rise.trader.orderbook.consumer.dao;

import lombok.Builder;
import lombok.Data;
import rise.trader.orderbook.common.pojo.Account;
import rise.trader.orderbook.common.pojo.OrderStatus;
import rise.trader.orderbook.common.pojo.Side;
import rise.trader.orderbook.common.pojo.Symbol;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class Order {

    @Id
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

    @JsonProperty(value = "status")
    private OrderStatus status;

    // ToDo: Review this later, might be a bad approach.
    public static OrderBuilder builder() {
        return new OrderBuilder();
    }
}
