package msacqrses.command;


public class AddToCartCommand {

    private final String cartId;
    private final int item;

    public AddToCartCommand(String cartId, int item) {
        this.cartId = cartId;
        this.item = item;
    }

    public String getCartId() {
        return cartId;
    }

    public int getItem() {
        return item;
    }
}
