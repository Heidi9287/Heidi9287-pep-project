package Service;
import Model.Account;
import DAO.SocialMediaDao;
import java.util.List;

public class AccountService {
    SocialMediaDao socialMediaDao;
    public Object LoginResult;

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
    public boolean isUsernameDuplicate(String username) {
        List<Account> allAccounts=socialMediaDao.getAllAccounts();
        for (Account account : allAccounts) {
            if (account.getUsername().equals(username)) {
                return true; // Duplicate username found
            }
        }
        return false; // No duplicate username found
    }
    public class LoginResult {
        private final boolean success;
        private final Account account;
    
        public LoginResult(boolean success, Account account) {
            this.success = success;
            this.account = account;
        }
    
        public boolean isSuccess() {
            return success;
        }
    
        public Account getAccount() {
            return account;
        }
    }
    
    public LoginResult userCanLogin(Account account) {
        if (account == null) {
            return new LoginResult(false, null);
        }
    
        Account accountFound = socialMediaDao.userLogin(account);
    
        if (accountFound != null && account.getPassword().equals(accountFound.getPassword())) {
            return new LoginResult(true, accountFound);
        } else {
            return new LoginResult(false, null);
        }
    }

}
