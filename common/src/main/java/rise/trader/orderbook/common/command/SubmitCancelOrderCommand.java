package rise.trader.orderbook.common.command;

import lombok.Getter;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Getter
public class SubmitCancelOrderCommand {

    @TargetAggregateIdentifier
    private String tradeId;

    public SubmitCancelOrderCommand(String tradeId) {
        this.tradeId = tradeId;
    }
}
