package msacqrses.eventhandler;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import msacqrses.event.RemoveFromCartEvent;

import javax.sql.DataSource;

@Component
public class RemoveFromCartEventHandler {

    @Autowired
    DataSource dataSource;

    @EventHandler
    public void handleRemoveFromCartEvent(RemoveFromCartEvent event) {
    	
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Get current state from event
        String cartId = event.getCartId();
        int items = event.getItems();
        int itemsToBeRemoved = event.getItemsRemoved();
        
        System.out.println("items>>>>>"+items);
        
        System.out.println("itemsToBeRemoved>>>>>"+itemsToBeRemoved);
        
        int newItems = items - itemsToBeRemoved;
        
        // Update cartview
        String update = "UPDATE cartview SET items = ? WHERE cartid = ?";
        jdbcTemplate.update(update, new Object[]{newItems, cartId});
        
    }
}
