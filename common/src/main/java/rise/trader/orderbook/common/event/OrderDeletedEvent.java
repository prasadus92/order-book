package rise.trader.orderbook.common.event;

import lombok.Getter;

@Getter
public class OrderDeletedEvent {

    private String tradeId;

    public OrderDeletedEvent(String tradeId) {
        this.tradeId = tradeId;
    }
}
