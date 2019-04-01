package rise.trader.orderbook.common.event;

import lombok.Getter;
import rise.trader.orderbook.common.command.BaseUpdateCommand;
import rise.trader.orderbook.common.pojo.OrderStatus;

@Getter
public class OrderUpdatedEvent {

    private String tradeId;

    private OrderStatus status;

    public OrderUpdatedEvent(BaseUpdateCommand command) {
        this.tradeId = (String) command.tradeId;
        this.status = (OrderStatus) command.status;
    }
}
