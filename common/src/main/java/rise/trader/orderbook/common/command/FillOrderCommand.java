package rise.trader.orderbook.common.command;

import rise.trader.orderbook.common.pojo.OrderStatus;

public class FillOrderCommand extends BaseUpdateCommand {

    public FillOrderCommand(String tradeId, OrderStatus status) {
        super(tradeId, status);
    }
}
