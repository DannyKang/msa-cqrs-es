package msacqrses.controller;

//import exploringaxon.replay.AccountCreditedReplayEventHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
//import org.axonframework.eventhandling.replay.ReplayingCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import msacqrses.command.AddToCartCommand;
import msacqrses.command.RemoveFromCartCommand;

/**
 * Command Controller for Cart.
 */

//@Controller
//@CrossOrigin
@RestController
public class CommandController {

/*    @Autowired
    @Qualifier("replayCluster")
    ReplayingCluster replayCluster;
*/
/*    @Autowired
    AccountCreditedReplayEventHandler replayEventHandler;
*/

    @Autowired
    private CommandGateway commandGateway;

/*    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("name", "venkatesh");
        return "Command Controller Root. Credit/Debit Accounts Path : <root>/credit/{accountNumber}/{amount} ,  <root>/debit/{accountNumber}/{amount}";
    }
*/

 /*
    @RequestMapping("/about")
    public String about() {
        return "about";
    }
*/

/*    @RequestMapping("/debit")
    @Transactional
    @ResponseBody
    public void doDebit(@RequestParam("acc") String accountNumber, @RequestParam("amount") double amount) {
System.out.println("-1 IndexController doDebit");
    	DebitAccountCommand debitAccountCommandCommand = new DebitAccountCommand(accountNumber, amount);
        commandGateway.send(debitAccountCommandCommand);
    }
*/

    @RequestMapping("/remove/{cartId}/{item}")
    @Transactional
    //@ResponseBody
    public ResponseEntity doRemove(@PathVariable String cartId, @PathVariable int item) {
System.out.println("-1 IndexController doRemove");
System.out.println("cartId>doRemove> "+cartId);
System.out.println("item>doRemove> "+item);
		RemoveFromCartCommand removeCartCommand = new RemoveFromCartCommand(cartId, item);
        commandGateway.send(removeCartCommand);
        
        return new ResponseEntity<>("Remove event generated. Status: "+ HttpStatus.OK, HttpStatus.OK);
    }

    
/*    @RequestMapping("/credit")
    @Transactional
    @ResponseBody
    public void doCredit(@RequestParam("acc") String accountNumber, @RequestParam("amount") double amount) {
        CreditAccountCommand creditAccountCommandCommand = new CreditAccountCommand(accountNumber, amount);
        commandGateway.send(creditAccountCommandCommand);
    }
*/
    @RequestMapping("/add/{cartId}/{item}")
    @Transactional
    //@ResponseBody
    public ResponseEntity doAdd(@PathVariable String cartId, @PathVariable int item) {
    	System.out.println("-1 IndexController doAdd");

    	System.out.println("cartId>doAdd> "+cartId);
    	System.out.println("item>doAdd> "+item);

    	AddToCartCommand addCartCommand = new AddToCartCommand(cartId, item);
        commandGateway.send(addCartCommand);
        
        return new ResponseEntity<>("Add event generated. Status: "+ HttpStatus.OK, HttpStatus.OK);
    }
    
    /*    @RequestMapping("/events")
    public String doReplay(Model model) {
        replayCluster.startReplay();
        model.addAttribute("events",replayEventHandler.getAudit());
        return "events";
    }
*/}
