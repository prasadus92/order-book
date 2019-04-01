package rise.trader.orderbook.common.command;

import rise.trader.orderbook.common.pojo.OrderStatus;

public class SubmitOrderCommand extends BaseUpdateCommand {

    public SubmitOrderCommand(String tradeId, OrderStatus status) {
        super(tradeId, status);
    }
}
