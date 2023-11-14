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
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.post("/register", this::registerAccountsHandler);
        app.post("/messages", this::createMessageHandler);
        app.post("/login", this::loginHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
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

    private void loginHandler(Context ctx) throws JsonProcessingException {
        try{     
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        if (accountService.userCanLogin(account)) {
            ctx.status(200);
        } else {
            ctx.status(401);
        }}
        catch (JsonProcessingException e) {
            // Print or log the stack trace for detailed error information
            e.printStackTrace();
    
            ctx.status(500).result("Internal server error: Error processing JSON payload");
        }
    }

    private boolean isUsernameDuplicate(String username) {
        return accountService.isUsernameDuplicate(username);
    }

    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.createMessage(message);
        if (addedMessage == null) {
            ctx.status(400);
        } else if (addedMessage.message_text.isEmpty()
                || addedMessage.message_text.length() > 255) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(addedMessage));
        }
    }

    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);
        if (message == null) {
            ctx.status(200);
        } else
            ctx.json(message);

    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message_id, message);
        if (updatedMessage == null) {
            ctx.status(400);
        } else if (updatedMessage.message_text.isEmpty() || updatedMessage.message_text.length() > 255) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }
    }
}