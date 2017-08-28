package msacqrses.event;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class AddToCartEvent {

    private final String cartId;
    private final int itemAdded;
    private final int items;
    private final long timeStamp;

    public AddToCartEvent(String cartId, int itemAdded, int items) {
        this.cartId = cartId;
        this.itemAdded = itemAdded;
        this.items = items;
        ZoneId zoneId = ZoneId.systemDefault();
        this.timeStamp = LocalDateTime.now().atZone(zoneId).toEpochSecond();
    }

    public String getCartId() {
        return cartId;
    }

    public int getItemAdded() {
        return itemAdded;
    }

    public int getItems() {
        return items;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
