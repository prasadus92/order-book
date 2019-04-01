package rise.trader.orderbook.common.command;

import rise.trader.orderbook.common.pojo.OrderStatus;

public class PartiallyFillOrderCommand extends BaseUpdateCommand {

    public PartiallyFillOrderCommand(String tradeId, OrderStatus status) {
        super(tradeId, status);
    }
}
