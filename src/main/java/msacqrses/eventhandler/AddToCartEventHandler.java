package msacqrses.eventhandler;

import org.axonframework.domain.Message;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventhandling.annotation.Timestamp;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import msacqrses.event.AddToCartEvent;

import javax.sql.DataSource;
@Component
public class AddToCartEventHandler {

    @Autowired
    DataSource dataSource;

    @EventHandler
    public void handleAddToCartEvent(AddToCartEvent event, Message msg) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Get current from event
        String cartId = event.getCartId();
        int items = event.getItems();
        int itemToBeAdded = event.getItemAdded();
        int newItems = items + itemToBeAdded;

        System.out.println("items>> add>>> "+items);
        System.out.println("itemToBeAdded>> add>>> "+itemToBeAdded);
        
        System.out.println("newitems>> add>>> "+newItems);
        // Update cartview
        String updateQuery = "UPDATE cartview SET items = ? WHERE cartid = ?";
        jdbcTemplate.update(updateQuery, new Object[]{newItems, cartId});

    }

}
