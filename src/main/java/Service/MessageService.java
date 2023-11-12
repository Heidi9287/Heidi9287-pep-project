package Service;
import Model.Message;
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

        Message createMessage= socialMediaDao.createMessage(message);
        return createMessage;
    }
}

