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

    public List<Message> getAllMessagesByUser(int posted_by) {
        List<Message> messages = socialMediaDao.getAllMessageByUser(posted_by);
        return messages;
    }

    public Message getMessageById(int message_id) {
        return socialMediaDao.getMessageById(message_id);
    }

    public Message updateMessage(int message_id, Message message) {
        Message currentMessage = socialMediaDao.getMessageById(message_id);
        System.out.println("current"+currentMessage);
        System.out.println("update"+ message);

        if (currentMessage != null&&message.message_text.length()<255) {
            socialMediaDao.updateMessage(message_id, message);
            return socialMediaDao.getMessageById(message_id);
        } else
            return null;
    }
    public Message deleteMessage(int message_id){
        Message toBeDeleted=socialMediaDao.getMessageById(message_id);
        if(toBeDeleted!=null){
        return toBeDeleted;
        }else{return null;}
    }
}
