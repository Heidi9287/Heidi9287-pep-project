package Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    public SocialMediaController(){
     accountService = new AccountService();
     }
public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerAccountsHandler);

        return app;
    }

    private void registerAccountsHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper=new ObjectMapper();
        Account account=mapper.readValue(context.body(),Account.class);
        Account addedAccount=accountService.addAccount(account);
        if(addedAccount==null){context.status(404);}
        if(addedAccount.username==""){context.status(400);}
        else{   context.json(mapper.writeValueAsString(addedAccount));}

    }

}