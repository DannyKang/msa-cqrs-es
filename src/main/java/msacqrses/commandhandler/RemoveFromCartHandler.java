package msacqrses.commandhandler;

import msacqrses.command.RemoveFromCartCommand;
import msacqrses.model.Cart;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RemoveFromCartHandler {

    private Repository repository;

    @Autowired
    public RemoveFromCartHandler(Repository repository) {
        this.repository = repository;
    }

    @CommandHandler
    public void handle(RemoveFromCartCommand removeFromCartCommand){
    	System.out.println("1 RemoveFromCartHandler handle");
    	System.out.println("RemoveFromCartHandler.handle>>>getCartId>>>>>>> "+removeFromCartCommand.getCartId());
    	Cart cartToBeRemoved = (Cart) repository.load(removeFromCartCommand.getCartId());
    	System.out.println("RemoveFromCartHandler.handle>>>cartToBeRemoved>>>> "+cartToBeRemoved);

    	System.out.println("RemoveFromCartHandler.handle>>>items in cart>>>> "+cartToBeRemoved.getItems());
    	
    	System.out.println("RemoveFromCartHandler.handle>>>items to be removed>>>> "+removeFromCartCommand.getItem());
    	
    	cartToBeRemoved.removeCart(removeFromCartCommand.getItem());
        
    }
}
