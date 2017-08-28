package msacqrses.commandhandler;

import msacqrses.command.AddToCartCommand;
import msacqrses.model.Cart;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddToCartCommandHandler {

    private Repository repository;

    @Autowired
    public AddToCartCommandHandler(Repository repository) {
        this.repository = repository;
    }

    @CommandHandler
    public void handle(AddToCartCommand addToCartCommand){
        Cart cartToBeAdded = (Cart) repository.load(addToCartCommand.getCartId());
        cartToBeAdded.addCart(addToCartCommand.getItem());
    }

}
