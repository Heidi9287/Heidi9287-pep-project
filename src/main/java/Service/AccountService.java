package Service;

import Model.Account;
import DAO.SocialMediaDao;
import java.util.List;

public class AccountService {
    SocialMediaDao socialMediaDao;

    public AccountService() {
        socialMediaDao = new SocialMediaDao();
    }

    public AccountService(SocialMediaDao socialMediaDao) {
        this.socialMediaDao = socialMediaDao;
    }
    public Account addAccount(Account account) {

        Account addedAccount= socialMediaDao.registerAccounts(account);
        return addedAccount;
    }

}
