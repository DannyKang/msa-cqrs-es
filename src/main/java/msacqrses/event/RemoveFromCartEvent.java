package msacqrses.event;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class RemoveFromCartEvent {
    private final String cartId;
    private final int itemsRemoved;
    private final int items;
    private final long timeStamp;

    public RemoveFromCartEvent(String cartId, int itemsRemoved, int items) {
        this.cartId = cartId;
        this.itemsRemoved = itemsRemoved;
        this.items = items;
        ZoneId zoneId = ZoneId.systemDefault();
        this.timeStamp = LocalDateTime.now().atZone(zoneId).toEpochSecond();
        
        System.out.println("3 RemoveFromCartEvent Constructor");
    }

    public String getCartId() {
        return cartId;
    }

    public int getItemsRemoved() {
        return itemsRemoved;
    }

    public int getItems() {
        return items;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
