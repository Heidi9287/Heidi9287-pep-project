package Service;

import Model.Message;

import java.util.List;

import DAO.SocialMediaDao;

public class MessageService {
    SocialMediaDao socialMediaDao;

    public MessageService() {
        socialMediaDao = new SocialMediaDao();
    }

    public MessageService(SocialMediaDao socialMediaDao) {
        this.socialMediaDao = socialMediaDao;
    }

    public Message createMessage(Message message) {

        Message createMessage = socialMediaDao.createMessage(message);
        return createMessage;
    }

    public List<Message> getAllMessages() {
        return socialMediaDao.getAllMessages();
    }

    public Message updateMessage(int message_id, Message message) {
        Message currentMessage = socialMediaDao.getMessageById(message_id);
        System.out.println("Updating message with ID: " + message_id);
        System.out.println("Original Message: " + socialMediaDao.getMessageById(message_id));
        System.out.println("Updated Message: " + message);
    
        if (currentMessage != null) {
            socialMediaDao.updateMessage(message_id, message);
            return socialMediaDao.getMessageById(message_id);
        } else
            return null;
    }
}
