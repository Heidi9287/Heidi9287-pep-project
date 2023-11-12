package Controller;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
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
    MessageService messageService;

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/messages", this::getAllMessagesHandler);
        app.post("/register", this::registerAccountsHandler);
        app.post("/messages", this::createMessageHandler);
        return app;
    }

    private void registerAccountsHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        if (isUsernameDuplicate(account.getUsername())) {
            context.status(400);
        }
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount == null) {
            context.status(404);
        }
        if (addedAccount.username == "" || addedAccount.password.length() < 4) {
            context.status(400);
        } else {
            context.json(mapper.writeValueAsString(addedAccount));
        }

    }

    private boolean isUsernameDuplicate(String username) {
        System.out.println(username);
        return accountService.isUsernameDuplicate(username);
    }

    private void createMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.createMessage(message);
        if (addedMessage == null) {
            context.status(400);
        }
        else if (addedMessage.message_text.isEmpty()
                || addedMessage.message_text.length() > 255) {
            context.status(400);
        } else {
            context.json(mapper.writeValueAsString(addedMessage));
        }
    }
    private void getAllMessagesHandler(Context ctx) {
        List <Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }
}   