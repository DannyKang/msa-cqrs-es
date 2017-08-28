package msacqrses.event;

/**
 * Created by Dadepo Aderemi.
 */
public class CartCreatedEvent {

    private final String cartId;

    public CartCreatedEvent(String cartId) {
        this.cartId = cartId;
    }

    public String getCartId() {
        return cartId;
    }
}
