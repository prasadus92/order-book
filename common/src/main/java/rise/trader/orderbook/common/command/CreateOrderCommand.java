package rise.trader.orderbook.common.command;

import rise.trader.orderbook.common.pojo.OrderStatus;

public class CreateOrderCommand extends BaseUpdateCommand {

    public CreateOrderCommand(String tradeId, OrderStatus status) {
        super(tradeId, status);
    }
}
