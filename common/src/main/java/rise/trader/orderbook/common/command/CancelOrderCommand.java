package rise.trader.orderbook.common.command;

import rise.trader.orderbook.common.pojo.OrderStatus;

public class CancelOrderCommand extends BaseUpdateCommand {

    public CancelOrderCommand(String tradeId, OrderStatus status) {
        super(tradeId, status);
    }
}
