package msacqrses.command;

public class RemoveFromCartCommand {

    private final String cartId;
    private final int item;

    public RemoveFromCartCommand(String cartId, int item) {
        this.cartId = cartId;
        this.item = item;
   System.out.println("0 RemoveFromCartCommand Constructor");
    }

    public String getCartId() {
        return cartId;
    }

    public int getItem() {
        return item;
    }
}
