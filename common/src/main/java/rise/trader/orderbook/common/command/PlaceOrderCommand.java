package rise.trader.orderbook.common.command;

import lombok.Getter;
import lombok.Setter;
import rise.trader.orderbook.common.pojo.Account;
import rise.trader.orderbook.common.pojo.Side;
import rise.trader.orderbook.common.pojo.Symbol;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class PlaceOrderCommand {

    @TargetAggregateIdentifier
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
}
