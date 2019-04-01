package rise.trader.orderbook.common.pojo;

import lombok.Data;

@Data
public class Symbol {

    private String exchange;
    private String from;
    private String to;
}
