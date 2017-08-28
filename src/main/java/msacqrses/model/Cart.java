package msacqrses.model;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import msacqrses.event.AddToCartEvent;
import msacqrses.event.CartCreatedEvent;
import msacqrses.event.RemoveFromCartEvent;

public class Cart extends AbstractAnnotatedAggregateRoot {
    private static final long serialVersionUID = 8723320580782813954L;

    @AggregateIdentifier
    private String cartid;

    private int items;

    public Cart() {
    }

    public Cart(String cartId) {
        apply(new CartCreatedEvent(cartId));
    }

    @EventSourcingHandler
    public void applyCartCreation(CartCreatedEvent event) {
        this.cartid = event.getCartId();
        this.items = 0;
    }

    public void removeCart(int removeitem) {

    	System.out.println("2 Cart Remove");
    	
            /**
             * Instead of changing the state directly we apply an event
             * that conveys what happened.
             *
             * The event thus applied is stored.
             */
     if(this.items > removeitem && removeitem > 0)
    	apply(new RemoveFromCartEvent(this.cartid, removeitem, this.items));

    }

    @EventSourcingHandler
    private void applyCartRemove(RemoveFromCartEvent event) {
        /**
         * This method is called as a reflection of applying events stored in the event store.
         * Consequent application of all the events in the event store will bring the Account
         * to the most recent state.
         */
    	System.out.println("4 applyCartRemove");
    	
    	System.out.println("this.items>>>>>> "+this.items);

    	this.items -= event.getItemsRemoved();
    }

    public void addCart(int item) {
            /**
             * Instead of changing the state directly we apply an event
             * that conveys what happened.
             *
             * The event thus applied is stored.
             */
    	if(item > 0)    
    		apply(new AddToCartEvent(this.cartid, item, this.items));
    }

    @EventSourcingHandler
    private void applyCartAdd(AddToCartEvent event) {
        /**
         * This method is called as a reflection of applying events stored in the event store.
         * Consequent application of all the events in the event store will bring the Account
         * to the most recent state.
         */
        this.items += event.getItemAdded();
    }

    public int getItems() {
        return items;
    }

    public void setIdentifier(String id) {
        this.cartid = id;
    }

    @Override
    public Object getIdentifier() {
        return cartid;
    }
}
