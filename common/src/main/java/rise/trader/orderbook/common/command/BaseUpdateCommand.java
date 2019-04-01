package rise.trader.orderbook.common.command;

import rise.trader.orderbook.common.pojo.OrderStatus;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.springframework.util.Assert;

public class BaseUpdateCommand {
    @TargetAggregateIdentifier
    public final String tradeId;

    public final OrderStatus status;

    public BaseUpdateCommand(String tradeId, OrderStatus status) {
        Assert.notNull(tradeId, "ID cannot be null");
        Assert.notNull(status, "Status cannot be null");
        this.tradeId = tradeId;
        this.status = status;
    }
}
